package com.geekadonis.critterz;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

/**
 * Let's start the day right
 */
@Slf4j
@Service
@Transactional(readOnly=true)
public class CritterzService {
    private final CritterzRepository critterzRepository;
    private final RestClient restClient;

    public CritterzService(CritterzRepository critterzRepository, RestClient.Builder restClientBuilder) {
        this.critterzRepository = critterzRepository;
        this.restClient = restClientBuilder.build();
    }

    @Transactional
    public void fetchImagesAndCreateCritters(CritterType critterType, int count, int width, int height) {
        List<Critter> critters = IntStream.rangeClosed(1, count)
            .mapToObj(value -> fetchImageAndCreateCritter(critterType, width, height))
            .toList();
        critterzRepository.saveAll(critters);
        log.info(critters.size() + " critters created");
    }

    Iterable<Critter> getAll() {
        final var all = critterzRepository.findAll();
        log.info("getAll(): {}", all.spliterator().estimateSize());
        return all;
    }

    public Optional<Critter> getLastCritter() {
        var critter = critterzRepository.findFirstByOrderByIdDesc();
        return critter;
    }

    ////

    private Critter fetchImageAndCreateCritter(CritterType critterType, int width, int height) {
        ResponseEntity<byte[]> response = null;
        var uri = getUriOfCritterApi(critterType, width, height);
        try {
            response = this.restClient
                .get()
                .uri(uri)
                .retrieve()
                .toEntity(byte[].class);
        } catch (HttpServerErrorException e) {
            throw new RuntimeException("Failed to fetch image at: " + uri + ", with error: " + e.getMessage());
        }

        if (!HttpStatus.OK.equals(response.getStatusCode())) {
            throw new RuntimeException("Expected status OK, but got: " +  response.getStatusCode());
        }

        var result = Critter.builder()
            .imageData(response.getBody())
            .critterType(critterType)
            .build();
        return result;
    }

    private String getUriOfCritterApi(CritterType critterType, int width, int height) {
        var uri = switch (critterType) {
            case BEAR -> "https://placebear.com";
            case DOG -> "https://place.dog";
            case KITTEN -> "https://placekitten.com";
        };
        uri += "/" + width + "/" + height;
        return uri;
    }
}
