package com.example.demo.services.mapper.Review;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.example.demo.DTO.Review.CreateReviewDTO;
import com.example.demo.DTO.Review.ReviewDTO;
import com.example.demo.DTO.Review.UpdateReviewDTO;
import com.example.demo.Exceptions.ReviewRatingException;
import com.example.demo.models.Job;
import com.example.demo.models.Review;
import com.example.demo.models.User;

@Component
public class ReviewMapperImpl implements ReviewMapper {

    @Override
    public ReviewDTO ReviewtoReviewDTO(Review review) {

        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setText(review.getText());
        reviewDTO.setJobId(review.getJob().getJobId());
        reviewDTO.setJobTitle(review.getJob().getTitle());
        reviewDTO.setUserEmailReviewed(review.getUserReviewed().getEmail());
        reviewDTO.setUserEmailReviewer(review.getUserReviewer().getEmail());
        reviewDTO.setCreated_at(review.getCreated_at());
        reviewDTO.setUserReviewer_img(review.getUserReviewer().getPicture());
        reviewDTO.setRating(review.getRating());
        return reviewDTO;

    }

    @Override
    public Review CreateReviewDTOtoReview(CreateReviewDTO createReviewDTO, Job job,
            User userReviewed, User userReviewer) {

        Review review = new Review();
        review.setJob(job);
        review.setUserReviewed(userReviewed);
        review.setUserReviewer(userReviewer);
        if (createReviewDTO.getRating() > 0 && createReviewDTO.getRating() < 6) {
            review.setRating(createReviewDTO.getRating());
        } else {
            throw new ReviewRatingException("La calificacion debe ser entre 1 y 5");
        }
        review.setText(createReviewDTO.getText());
        review.setCreated_at(LocalDateTime.now());
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
    public Review updateReview(Review review, UpdateReviewDTO updateReviewDTO) {

        if (updateReviewDTO.getRating() != null && updateReviewDTO.getRating() > 0 && updateReviewDTO.getRating() < 6) {
            review.setRating(updateReviewDTO.getRating());
        }
        if (updateReviewDTO.getReviewText() != null) {
            review.setText(updateReviewDTO.getReviewText());
        }
        return review;
    }

}
