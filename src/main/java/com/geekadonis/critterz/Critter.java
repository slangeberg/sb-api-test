package com.geekadonis.critterz;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class Critter {
    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    @ToString.Include
    private Long id;

    private CritterType critterType;

    @JsonIgnore
    @Lob
    private byte[] imageData;
}
