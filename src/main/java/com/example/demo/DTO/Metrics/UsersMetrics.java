package com.example.demo.DTO.Metrics;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UsersMetrics {

    long totalUsers;
    long totalActiveUsers;
    Double averageActiveUsers;
    long totalUsersCustomers;
    long totalUsersProviders;

}
