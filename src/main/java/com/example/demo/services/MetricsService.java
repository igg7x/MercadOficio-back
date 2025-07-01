package com.example.demo.services;

import org.springframework.stereotype.Service;

import com.example.demo.DTO.Metrics.CategoriesMetrics;
import com.example.demo.DTO.Metrics.UsersMetrics;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class MetricsService {

    private final UserService userService;
    private final CategoryService categoryService;
    private final UserOfferingService userOfferingService;
    private final UserCustomerService userCustomerService;

    public UsersMetrics getUsersMetrics() {
        return new UsersMetrics(userService.getTotalUsers(), userService.getTotalActiveUsers(),
                userService.getAverageActiveUsers(), userOfferingService.getTotalUsersOfferings(),
                userCustomerService.getTotalUsersCustomers());
    }

    public CategoriesMetrics getCategoriesMetrics() {
        return new CategoriesMetrics(categoryService.getTotalCategories(), categoryService.getTotalActiveCategories(),
                categoryService.getTotalActiveJobs(), userService.getTotalActiveUsers());
    }

}
