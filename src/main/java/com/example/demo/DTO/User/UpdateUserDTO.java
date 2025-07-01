package com.example.demo.DTO.User;

import java.util.Set;

import com.example.demo.auth.Roles;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserDTO {

    private String location;
    private String biography;
    private Long phone;
    private Set<Roles> newRoles;
    // private List<CategorieDTO> userCategories;
}
