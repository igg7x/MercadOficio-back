package com.example.demo.DTO.User;

import java.util.List;

import com.example.demo.DTO.Categories.CategorieDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserDTO {

    private String biography;

    private String name;

    private String surname;

    private List<CategorieDTO> userCategories;
}
