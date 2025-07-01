package com.example.demo.DTO.Metrics;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CategoriesMetrics {

    long totalCategories;
    long totalActiveCategories;
    long totalActiveJobs;
    long totalActiveUsers;
}
