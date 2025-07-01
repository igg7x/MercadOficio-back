package com.example.demo.DTO.Job;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateJobDTO {
    private String title;
    private String userOfferingEmail;
    private LocalDate deadline_date;
    private String description;
}
