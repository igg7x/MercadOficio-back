package com.example.demo.services.mapper.Review;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;

import com.example.demo.DTO.Review.CreateReviewDTO;
import com.example.demo.DTO.Review.ReviewDTO;
import com.example.demo.models.Review;
import com.example.demo.models.UserCustomer;
import com.example.demo.models.UserOffering;

@Mapper
public interface ReviewMapper {

    ReviewDTO ReviewtoReviewDTO(Review review, String userEmailReviewer, String userEmailReviewed);

    Review ReviewDTOtoReview(ReviewDTO reviewDTO);

    CreateReviewDTO ReviewtoCreateReviewDTO(Review review);

    Review CreateReviewDTOtoReview(CreateReviewDTO createReviewDTO, UserCustomer userCustomer,
            UserOffering userOffering);

    @BeanMapping(nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
    Review updateReviewFromDTO(ReviewDTO reviewDTO, Review review);

    List<ReviewDTO> ReviewstoReviewDTOs(List<Review> reviews);

    List<ReviewDTO> ReviewListToReviewDTOList(List<Review> reviews, String userEmailReviewed);
}
