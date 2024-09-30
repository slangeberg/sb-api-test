package com.geekadonis.critterz;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

/**
 * Let's start the day right
 */
@Slf4j
@Service
public class CritterzService {
    private final CritterzRepository critterzRepository;
    private final RestClient restClient;

    public CritterzService(CritterzRepository critterzRepository, RestClient.Builder restClientBuilder) {
        this.critterzRepository = critterzRepository;
        this.restClient = restClientBuilder.build();
    }

    public void fetchImagesAndCreateCritters(CritterType critterType, int count, int width, int height) {
        List<Critter> critters = IntStream.rangeClosed(1, count)
            .mapToObj(value -> fetchImageAndCreateCritter(critterType, width, height))
            .toList();
        critterzRepository.saveAll(critters);
        log.info(critters.size() + " critters created");
    }

    Iterable<Critter> findAll() {
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
        Critter result;
        var uri = getUriOfCritterApi(critterType, width, height);
        try {
            ResponseEntity<byte[]> response = this.restClient
                .get()
                .uri(uri)
                .retrieve()
                .toEntity(byte[].class);

            if (!HttpStatus.OK.equals(response.getStatusCode())) {
                throw new RuntimeException("Expected status OK, but got: " +  response.getStatusCode());
            }

            result = Critter.builder()
                .imageData(response.getBody())
                .critterType(critterType)
                .build();

        } catch (HttpServerErrorException e) {
            throw new RuntimeException("Failed to fetch image at: " + uri + ", with error: " + e.getMessage());
        }

        return result;
    }

    String getUriOfCritterApi(CritterType critterType, int width, int height) {
        var uri = switch (critterType) {
            case BEAR -> "https://placebear.com";
            case DOG -> "https://place.dog";
            case KITTEN -> "https://placekitten.com";
        };
        uri += "/" + width + "/" + height;
        return uri;
    }
}
