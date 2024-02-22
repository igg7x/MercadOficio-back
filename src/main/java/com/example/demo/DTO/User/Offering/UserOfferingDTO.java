package com.example.demo.DTO.User.Offering;

import java.util.Date;
import java.util.List;

import com.example.demo.DTO.Review.ReviewDTO;
import com.example.demo.DTO.User.UserDTO;
import com.example.demo.models.UserCategories;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserOfferingDTO extends UserDTO {

    private String location;
    private Integer price;
    private Date workDayStart;
    private Date workDayEnd;
    private List<ReviewDTO> reviews;
    private List<UserCategories> categories;
}
