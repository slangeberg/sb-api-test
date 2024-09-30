package com.geekadonis.critterz;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Let's start the day right
 */
public interface CritterzRepository extends CrudRepository<Critter, Long> {
    Optional<Critter> findFirstByOrderByIdDesc();

    // TODO: could be used to filter down by type
    Optional<Critter> findFirstByCritterTypeOrderByIdDesc(CritterType critterType);
}
