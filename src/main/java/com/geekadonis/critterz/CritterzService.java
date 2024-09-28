package com.geekadonis.critterz;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Let's start the day right
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class CritterzService {
    private final CritterzRepository critterzRepository;

    Iterable<Critter> getAll() {
        final var all = critterzRepository.findAll();
        log.info("getAll(): {}", all);
        return all;
    }
}
