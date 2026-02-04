package com.example.controller;

import com.example.dto.RatingDTO;
import com.example.entity.Rating;
import com.example.service.RatingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin("*")
@RequestMapping("/api/rating")
public class RatingController {

    private final RatingService ratingService;

    @PostMapping("/create")
    public ResponseEntity<Void> createRating(
            @Valid @RequestBody RatingDTO ratingDTO) {
        ratingService.createRating(ratingDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<Rating>> getAllRatings() {
        return ResponseEntity.ok(ratingService.findAllRatings());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteRating(
            @RequestParam String customerId,
            @RequestParam int productId) {

        ratingService.delete(customerId, productId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/status")
    public ResponseEntity<Void> updateStatus(
            @RequestParam String customerId,
            @RequestParam int productId,
            @RequestParam int status) {

        ratingService.updateStatus(customerId, productId, status);
        return ResponseEntity.ok().build();
    }
}
