package com.geekadonis.critterz;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Let's start the day right
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/critterz")
public class CritterzController {

    private final CritterzService critterzService;

    @GetMapping
    public ResponseEntity getCritterz() {
        var all = critterzService.getAll();
        var response = ResponseEntity.ok().body(all);
        return response;
    }
}
