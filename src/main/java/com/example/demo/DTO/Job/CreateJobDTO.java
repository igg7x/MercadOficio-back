package com.example.demo.DTO.Job;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateJobDTO {
    private String userCustomerEmail;
    private String title;
    private String description;
    private String location;
    private LocalDate deadline_date;
    private String category;
}
