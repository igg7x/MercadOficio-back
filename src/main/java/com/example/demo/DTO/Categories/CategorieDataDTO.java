package com.example.demo.DTO.Categories;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategorieDataDTO {
    private String category_name;
    private Integer category_count;
    private long active_jobs_count;
    private Boolean category_status;
}
