package com.example.demo.services.mapper.Review;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.demo.DTO.Review.CreateReviewDTO;
import com.example.demo.DTO.Review.ReviewDTO;
import com.example.demo.models.Review;
import com.example.demo.models.UserCustomer;
import com.example.demo.models.UserOffering;

@Component
public class ReviewMapperImpl implements ReviewMapper {

    @Override
    public ReviewDTO ReviewtoReviewDTO(Review review, String userEmailReviewer, String userEmailReviewed) {

        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setText(review.getText());
        reviewDTO.setUserEmailReviewer(userEmailReviewer);
        reviewDTO.setUserEmailReviewed(userEmailReviewed);
        return reviewDTO;

    }

    @Override
    public Review CreateReviewDTOtoReview(CreateReviewDTO createReviewDTO, UserCustomer userCustomer,
            UserOffering userOffering) {

        Review review = new Review();
        review.setText(createReviewDTO.getText());
        review.setUserCustomer(userCustomer);
        review.setUserOffering(userOffering);
        review.setCreatedAt(Date.from(new Date().toInstant()));
        review.setNum_likes(0);
        return review;
    }

    @Override
    public Review ReviewDTOtoReview(ReviewDTO reviewDTO) {
        return null;
    }

    @Override
    public CreateReviewDTO ReviewtoCreateReviewDTO(Review review) {
        return null;
    }

    @Override
    public Review updateReviewFromDTO(ReviewDTO reviewDTO, Review review) {
        return null;
    }

    @Override
    public List<ReviewDTO> ReviewstoReviewDTOs(List<Review> reviews) {
        return null;
    }

    @Override
    public List<ReviewDTO> ReviewListToReviewDTOList(List<Review> reviews, String userEmailReviewed) {

        List<ReviewDTO> userOfferingReviews = new ArrayList<>();
        for (Review review : reviews) {
            userOfferingReviews.add(ReviewtoReviewDTO(review,
                    review.getUserCustomer().getUser().getEmail(), userEmailReviewed));
        }
        return userOfferingReviews;
    }

    @Override
    public ReviewDTO ReviewtoReviewDTO(Review review, String email) {
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setText(review.getText());
        reviewDTO.setUserEmailReviewer(email);
        return reviewDTO;
    }

}
