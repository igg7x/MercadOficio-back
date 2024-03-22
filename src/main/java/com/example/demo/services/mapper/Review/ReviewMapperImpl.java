package com.example.demo.services.mapper.Review;

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
        reviewDTO.setNum_likes(review.getNum_likes());
        reviewDTO.setDateReview(review.getCreatedAt());
        return reviewDTO;

    }

    @Override
    public Review ReviewDTOtoReview(ReviewDTO reviewDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'ReviewDTOtoReview'");
    }

    @Override
    public CreateReviewDTO ReviewtoCreateReviewDTO(Review review) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'ReviewtoCreateReviewDTO'");
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
    public Review updateReviewFromDTO(ReviewDTO reviewDTO, Review review) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateReviewFromDTO'");
    }

    @Override
    public List<ReviewDTO> ReviewstoReviewDTOs(List<Review> reviews) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'ReviewstoReviewDTOs'");
    }

}
