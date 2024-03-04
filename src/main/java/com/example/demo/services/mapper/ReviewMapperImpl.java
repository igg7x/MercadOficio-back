package com.example.demo.services.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.demo.DTO.Review.CreateReviewDTO;
import com.example.demo.DTO.Review.ReviewDTO;
import com.example.demo.models.Review;

@Component
public class ReviewMapperImpl implements ReviewMapper {

    @Override
    public ReviewDTO ReviewtoReviewDTO(Review review) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'ReviewtoReviewDTO'");
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
    public Review CreateReviewDTOtoReview(CreateReviewDTO createReviewDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'CreateReviewDTOtoReview'");
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
