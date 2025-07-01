package com.example.demo.DTO.Categories;

import org.springframework.lang.NonNull;

import lombok.Getter;

@Getter
public class UpdateCategoryStatusDTO {
    @NonNull
    private String category_name;
    @NonNull
    private Boolean category_status;
}
