package com.example.hrmanagementsystem.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.hrmanagementsystem.dto.PerformanceReviewDTO;
import com.example.hrmanagementsystem.service.PerformanceReviewService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/performance-reviews")
public class PerformanceReviewController {

    private final PerformanceReviewService reviewService;

    public PerformanceReviewController(
            PerformanceReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<PerformanceReviewDTO> createReview(
            @Valid @RequestBody PerformanceReviewDTO reviewDTO) {

        return new ResponseEntity<>(
                reviewService.createReview(reviewDTO),
                HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PerformanceReviewDTO>> getAllReviews() {

        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PerformanceReviewDTO> getReviewById(
            @PathVariable Long id) {

        return ResponseEntity.ok(reviewService.getReviewById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PerformanceReviewDTO> updateReview(
            @PathVariable Long id,
            @Valid @RequestBody PerformanceReviewDTO reviewDTO) {

        return ResponseEntity.ok(
                reviewService.updateReview(id, reviewDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {

        reviewService.deleteReview(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<PerformanceReviewDTO>> getReviewsByEmployee(
            @PathVariable Long employeeId) {

        return ResponseEntity.ok(
                reviewService.getReviewsByEmployee(employeeId));
    }

    @GetMapping("/rating/{rating}")
    public ResponseEntity<List<PerformanceReviewDTO>> getReviewsByRating(
            @PathVariable Integer rating) {

        return ResponseEntity.ok(
                reviewService.getReviewsByRating(rating));
    }
}
