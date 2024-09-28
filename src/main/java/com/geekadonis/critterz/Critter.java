package com.geekadonis.critterz;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

/**
 * Let's start the day right
 */
@Data
@Entity
public class Critter {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}