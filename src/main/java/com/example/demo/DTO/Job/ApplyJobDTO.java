package com.example.demo.DTO.Job;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApplyJobDTO {
    private String userOfferingEmail;
    private LocalDate applyDate;
}
