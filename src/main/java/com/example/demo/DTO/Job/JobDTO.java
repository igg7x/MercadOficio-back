package com.example.demo.DTO.Job;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JobDTO {
    private String jobId;
    private String title;
    private String description;
    private String category;
    private String userCustomerEmail;
    private String location;
    private LocalDate publish_date;
    private LocalDate deadline_date;
    private Boolean status;
}
