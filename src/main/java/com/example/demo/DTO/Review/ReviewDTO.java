package com.example.demo.DTO.Review;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class ReviewDTO {

    private String userEmailReviewer;
    private String userEmailReviewed;
    private String text;

}

// Path: src/main/java/com/example/demo/DTO/ReviewDTO.java
// hacer diagrama de clases con respecto a los DTO