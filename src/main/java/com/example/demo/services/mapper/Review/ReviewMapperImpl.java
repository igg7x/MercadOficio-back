package com.example.demo.services.mapper.Review;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.example.demo.DTO.Review.CreateReviewDTO;
import com.example.demo.DTO.Review.ReviewDTO;
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
        reviewDTO.setCreatedAt(review.getCreatedAt());
        return reviewDTO;

    }

    @Override
    public Review CreateReviewDTOtoReview(CreateReviewDTO createReviewDTO, Job job,
            User userReviewed, User userReviewer) {

        Review review = new Review();
        review.setJob(job);
        review.setUserReviewed(userReviewed);
        review.setUserReviewer(userReviewer);
        review.setRating(createReviewDTO.getRating());
        review.setText(createReviewDTO.getText());
        review.setCreatedAt(Date.from(new Date().toInstant()));

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

    // @Override
    // public Review updateReviewFromDTO(ReviewDTO reviewDTO, Review review) {
    // return null;
    // }

    // @Override
    // public List<ReviewDTO> ReviewstoReviewDTOs(List<Review> reviews) {
    // return null;
    // }

    // // @Override
    // // public List<ReviewDTO> ReviewListToReviewDTOList(List<Review> reviews,
    // String
    // // userEmailReviewed) {

    // // List<ReviewDTO> userOfferingReviews = new ArrayList<>();
    // // for (Review review : reviews) {
    // // userOfferingReviews.add(ReviewtoReviewDTO(review,
    // // review.getUserCustomer().getUser().getEmail(), userEmailReviewed));
    // // }
    // // return userOfferingReviews;
    // // }

    // @Override
    // public ReviewDTO ReviewtoReviewDTO(Review review, String email) {
    // ReviewDTO reviewDTO = new ReviewDTO();
    // reviewDTO.setText(review.getText());
    // reviewDTO.setUserEmailReviewer(email);
    // return reviewDTO;
    // }

    // @Override
    // public List<ReviewDTO> ReviewListToReviewDTOList(List<Review> reviews, String
    // userEmailReviewed) {
    // throw new UnsupportedOperationException("Unimplemented method
    // 'ReviewListToReviewDTOList'");
    // }

}
