package com.geekadonis.critterz;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Let's start the day right
 */
public interface CritterzRepository extends CrudRepository<Critter, Long> {
    Optional<Critter> findFirstByOrderByIdDesc();
    Optional<Critter> findFirstByCritterTypeOrderByIdDesc(CritterType critterType);
}
