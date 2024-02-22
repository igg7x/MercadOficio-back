package com.example.demo.DTO.User.Offering;

import java.util.Date;

import com.example.demo.DTO.User.UpdateUserDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserOfferingDTO extends UpdateUserDTO {

    private String emails;
    private String location;
    private Integer price;
    private Date workDayStart;
    private Date workDayEnd;
}
