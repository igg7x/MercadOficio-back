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
public class UserOfferingDTO extends UserDTO {

    // private Integer experience;
    // private Integer price;
    // private Time workDayStart;
    // private Time workDayEnd;
    private Integer calification;
    private List<CategorieDTO> categories;
}
