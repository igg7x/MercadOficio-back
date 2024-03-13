package com.example.demo.DTO.User.Offering;

import java.sql.Time;
import java.util.List;

import com.example.demo.DTO.Categories.CategorieDTO;
import com.example.demo.DTO.User.UpdateUserDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserOfferingDTO extends UpdateUserDTO {

    private String location;
    private Integer experience;
    private Integer price;
    private Time workDayStart;
    private Time workDayEnd;
    private List<CategorieDTO> categories;
}
