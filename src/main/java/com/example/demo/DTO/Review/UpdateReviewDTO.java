package com.example.demo.DTO.Review;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class UpdateReviewDTO {

    private Long reviewId;
    private String reviewText;

}
