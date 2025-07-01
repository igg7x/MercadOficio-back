package com.example.demo.DTO.Review;

import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class UpdateReviewDTO {

    private String userReviewerEmail;
    private String userReviewedEmail;
    @Nullable
    private String reviewText;
    @Nullable
    private Integer rating;
    private String jobId;

}
