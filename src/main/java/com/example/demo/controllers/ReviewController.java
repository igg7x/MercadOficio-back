package com.example.demo.controllers;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.Review.CreateReviewDTO;
import com.example.demo.DTO.Review.DeleteReviewDTO;
import com.example.demo.DTO.Review.ReviewDTO;
import com.example.demo.DTO.Review.UpdateReviewDTO;
import com.example.demo.services.ReviewService;

@RestController
@RequestMapping("/api/v1/reviews")
@CrossOrigin(origins = "*")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/get/{email}")
    private ResponseEntity<Page<ReviewDTO>> getReviewsByUserReviewedEmail(@PathVariable String email,
            Pageable pageable) {
        return ResponseEntity.ok(reviewService.getReviews(email, pageable, "userReviewed"));
    }

    @GetMapping("/get-created-by/{email}")
    private ResponseEntity<Page<ReviewDTO>> getReviewsByUserReviewerEmail(@PathVariable String email,
            Pageable pageable) {
        return ResponseEntity.ok(reviewService.getReviews(email, pageable, "userReviewer"));
    }

    @PostMapping("/create")
    private ResponseEntity<ReviewDTO> createReview(@Validated @RequestBody CreateReviewDTO createReviewDTO) {
        return ResponseEntity.ok(reviewService.createReview(createReviewDTO));
    }

    @PatchMapping("/update")
    private ResponseEntity<ReviewDTO> updateReview(
            @Validated @RequestBody UpdateReviewDTO updateReviewDTO) {
        return ResponseEntity.ok(reviewService.updateReview(updateReviewDTO));

    }

    @PatchMapping("/delete")
    private ResponseEntity<?> deleteReview(@Validated @RequestBody DeleteReviewDTO deleteReviewDTO) {
        // Map<String, String> response = new HashMap<>();
        // response.put("message", "Review deleted successfully");
        // return ResponseEntity.ok(response);
        try {
            reviewService.deleteReview(deleteReviewDTO);
            return ResponseEntity.ok(Map.of(
                    "message", "Reseña eliminada correctamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "message", "Ocurrió un error al eliminar la reseña",
                    "status", HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

}
