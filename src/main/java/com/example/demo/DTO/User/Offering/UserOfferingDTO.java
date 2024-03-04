package com.example.demo.DTO.User.Offering;

import java.sql.Time;
import java.util.List;

import com.example.demo.DTO.Review.ReviewDTO;
// import com.example.demo.DTO.Review.ReviewDTO;
import com.example.demo.DTO.User.UserDTO;
import com.example.demo.models.UserCategories;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserOfferingDTO extends UserDTO {

    private String location;
    private Integer price;
    private Time workDayStart;
    private Time workDayEnd;
    private List<ReviewDTO> reviews;
    private List<UserCategories> categories;
}
