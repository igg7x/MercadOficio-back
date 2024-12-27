package com.example.demo.DTO.User.Offering;

import java.util.List;

import com.example.demo.DTO.Categories.CategorieDTO;
import com.example.demo.DTO.User.UserDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsersOfferingDTO extends UserDTO {

    private Double calification;
    private List<CategorieDTO> categories;
// private Integer experience;
    // private Integer price;
    // private Time workDayStart;
    // private Time workDayEnd;

}
