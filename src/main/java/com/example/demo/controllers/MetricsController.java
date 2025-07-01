package com.example.demo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.Metrics.CategoriesMetrics;
import com.example.demo.DTO.Metrics.UsersMetrics;
import com.example.demo.auth.CurrentUserEmail;
import com.example.demo.services.MetricsService;
import com.example.demo.services.UserService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/metrics")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class MetricsController {

    private final MetricsService metricsService;
    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<UsersMetrics> getUsersMetrics(@CurrentUserEmail String email) {

        if (!userService.isAdmin(email)) {
            return ResponseEntity.badRequest().build();
        }
        UsersMetrics usersMetrics = metricsService.getUsersMetrics();
        try {
            return ResponseEntity.ok(usersMetrics);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/categories")
    public ResponseEntity<CategoriesMetrics> getCategoriesMetrics(@CurrentUserEmail String email) {
        if (!userService.isAdmin(email)) {
            return ResponseEntity.badRequest().build();
        }
        try {
            return ResponseEntity.ok(metricsService.getCategoriesMetrics());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
