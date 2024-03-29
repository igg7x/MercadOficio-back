package com.example.demo.DTO.Review;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateReviewDTO {
    private String userEmailReviewer; // user that is reviewing
    private String userEmailReviewed; // user that is being reviewed
    private String text;
}
