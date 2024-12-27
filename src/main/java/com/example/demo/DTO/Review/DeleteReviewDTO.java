package com.example.demo.DTO.Review;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class DeleteReviewDTO {
    private String userReviewerEmail;
    private String userReviewedEmail;
    private String jobId;
}
