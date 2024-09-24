package com.example.demo.DTO.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserDTO {

    private String location;
    private String biography;
    private Long phone;
    // @Nullable
    // private Set<Roles> newRoles;
    // private List<CategorieDTO> userCategories;
}
