package com.example.demo.services.mapper.Review;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;

import com.example.demo.DTO.Review.CreateReviewDTO;
import com.example.demo.DTO.Review.ReviewDTO;
import com.example.demo.models.Review;

@Mapper
public interface ReviewMapper {

    ReviewDTO ReviewtoReviewDTO(Review review);

    Review ReviewDTOtoReview(ReviewDTO reviewDTO);

    CreateReviewDTO ReviewtoCreateReviewDTO(Review review);

    Review CreateReviewDTOtoReview(CreateReviewDTO createReviewDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
    Review updateReviewFromDTO(ReviewDTO reviewDTO, Review review);

    List<ReviewDTO> ReviewstoReviewDTOs(List<Review> reviews);
}
