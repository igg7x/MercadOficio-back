package com.example.demo.DTO.User.Offering;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserOfferingDTO {

    private String location;
    private Integer price;
    private Date workDayStart;
    private Date workDayEnd;

}
