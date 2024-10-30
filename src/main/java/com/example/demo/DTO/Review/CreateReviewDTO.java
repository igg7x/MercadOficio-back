package com.example.demo.DTO.Review;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateReviewDTO {
    private String jobId;
    private String userEmailReviewer; // user that is reviewing // CHECK
    private String userEmailReviewed; // user that is being reviewed // CHECK
    private String text; // CHECK
    private int rating; // CHECK
}
