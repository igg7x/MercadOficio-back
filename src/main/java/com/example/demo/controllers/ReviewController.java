package com.example.demo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.Review.CreateReviewDTO;
import com.example.demo.DTO.Review.CreateReviewLikeDTO;
import com.example.demo.DTO.Review.ReviewDTO;
import com.example.demo.services.ReviewService;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/create")
    public ResponseEntity<ReviewDTO> createReview(@RequestBody CreateReviewDTO createReviewDTO) {
        return ResponseEntity.ok(reviewService.createReview(createReviewDTO));
    }

    @PostMapping("/like")
    public ResponseEntity<ReviewDTO> createReviewLike(@RequestBody CreateReviewLikeDTO createReviewLikeDTO) {

        if (reviewService.existsReviewLike(createReviewLikeDTO)) {
            return ResponseEntity.ok(reviewService.deleteReviewLike(createReviewLikeDTO));
        } else {
            return ResponseEntity.ok(reviewService.createReviewLike(createReviewLikeDTO));
        }
    }

}
