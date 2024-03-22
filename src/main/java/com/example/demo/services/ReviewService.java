package com.example.demo.services;

import org.springframework.stereotype.Service;

import com.example.demo.DTO.Review.CreateReviewDTO;
import com.example.demo.DTO.Review.ReviewDTO;
import com.example.demo.models.Review;
import com.example.demo.models.UserCustomer;
import com.example.demo.models.UserOffering;
import com.example.demo.repositories.ReviewRepository;
import com.example.demo.services.mapper.Review.ReviewMapper;

import jakarta.transaction.Transactional;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserOfferingService userOfferingService;
    private final UserCustomerService userCustomerService;
    private final ReviewMapper reviewMapper;

    public ReviewService(ReviewRepository reviewRepository, UserOfferingService userOfferingService,
            UserCustomerService userCustomerService, ReviewMapper reviewMapper) {
        this.reviewRepository = reviewRepository;
        this.userOfferingService = userOfferingService;
        this.userCustomerService = userCustomerService;
        this.reviewMapper = reviewMapper;
    }

    @Transactional
    public ReviewDTO createReview(CreateReviewDTO createReviewDTO) {

        UserCustomer userCustomer = userCustomerService.getUserCustomer(createReviewDTO.getUserEmailReviewer());
        UserOffering userOffering = userOfferingService.getUserOffering(createReviewDTO.getUserEmailReviewed());

        Review reviewCreated = reviewMapper.CreateReviewDTOtoReview(createReviewDTO, userCustomer, userOffering);
        ;

        reviewCreated = reviewRepository.save(reviewCreated);

        return reviewMapper.ReviewtoReviewDTO(reviewCreated, createReviewDTO.getUserEmailReviewer(),
                createReviewDTO.getUserEmailReviewed());
    }
}
