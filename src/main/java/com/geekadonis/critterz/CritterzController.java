package com.geekadonis.critterz;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Let's start the day right
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/critterz")
public class CritterzController {

    private final CritterzService critterzService;

    @PostMapping
    public ResponseEntity<Object> fetchImagesAndSaveCritterz(
            @RequestParam CritterType critterType,
            @RequestParam(defaultValue = "1")
            @Min(value = 1, message = "Minimum 1 image needs to be fetched")
            @Max(value = 10, message = "Maximum 10 images can be fetched at once") int count,
            @RequestParam(defaultValue = "300", required = false) int width,
            @RequestParam(defaultValue = "300", required = false) int height) {

        critterzService.fetchImagesAndCreateCritters(critterType, count, width, height);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity getAllCritterz() {
        var all = critterzService.findAll();
        var response = ResponseEntity.ok().body(all);
        return response;
    }

    @GetMapping(value = "/last")
    public ResponseEntity<Critter> getLastCritter() {
        var body = critterzService.getLastCritter();
        return ResponseEntity.of(body);
    }

    @GetMapping(value = "/last/image", produces = {MediaType.IMAGE_JPEG_VALUE})
    public ResponseEntity<byte[]> getLastCritterImage() {
        var body = critterzService.getLastCritter();
        if (body.isPresent()) {
            return ResponseEntity.ok().body(body.get().getImageData());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}