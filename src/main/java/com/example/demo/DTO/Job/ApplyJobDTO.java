package com.example.demo.DTO.Job;

import java.time.LocalDate;

import lombok.Getter;

@Getter
public class ApplyJobDTO {
    private String userOfferingEmail;
    private LocalDate applyDate;
}
