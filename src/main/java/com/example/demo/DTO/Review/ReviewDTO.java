package com.example.demo.DTO.Review;

import java.time.LocalDate;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class ReviewDTO {
    private String userReviewer_img;
    private String jobId;
    private String jobTitle;
    private String userEmailReviewer;
    private String userEmailReviewed;
    private String text;
    private LocalDate created_at;
    private int rating;
}

// Path: src/main/java/com/example/demo/DTO/ReviewDTO.java
// hacer diagrama de clases con respecto a los DTO