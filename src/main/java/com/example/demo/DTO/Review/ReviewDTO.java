package com.example.demo.DTO.Review;

import java.util.Date;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class ReviewDTO {

    private Long userOfferingId;
    private Long userCustomerId;
    private Date dateReview;
    private String text;
    private Integer num_likes;

}

// Path: src/main/java/com/example/demo/DTO/ReviewDTO.java