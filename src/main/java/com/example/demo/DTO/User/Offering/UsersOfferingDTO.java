package com.example.demo.DTO.User.Offering;

import java.sql.Time;

import com.example.demo.DTO.User.UserDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsersOfferingDTO extends UserDTO {

    private Double calification;
    private String location;
    private Integer experience;
    private Integer price;
    private Time workDayStart;
    private Time workDayEnd;

}
