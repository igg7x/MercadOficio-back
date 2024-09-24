package com.example.demo.services.mapper.Review;

import org.mapstruct.Mapper;

import com.example.demo.DTO.Review.CreateReviewDTO;
import com.example.demo.DTO.Review.ReviewDTO;
import com.example.demo.models.Job;
import com.example.demo.models.Review;
import com.example.demo.models.User;

@Mapper
public interface ReviewMapper {

    ReviewDTO ReviewtoReviewDTO(Review review);

    Review ReviewDTOtoReview(ReviewDTO reviewDTO);

    CreateReviewDTO ReviewtoCreateReviewDTO(Review review);

    Review CreateReviewDTOtoReview(CreateReviewDTO createReviewDTO,
            Job job, User userReviewed, User userReviewer);

    // @BeanMapping(nullValuePropertyMappingStrategy =
    // org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
    // Review updateReviewFromDTO(ReviewDTO reviewDTO, Review review);

    // List<ReviewDTO> ReviewstoReviewDTOs(List<Review> reviews);

    // List<ReviewDTO> ReviewListToReviewDTOList(List<Review> reviews, String
    // userEmailReviewed);

    // ReviewDTO ReviewtoReviewDTO(Review review, String email);
}
