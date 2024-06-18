package com.example.demo.DTO.Job;

import java.time.LocalDate;

import org.springframework.lang.Nullable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApplyJobDTO {
    private String userOfferingEmail;
    @Nullable
    private LocalDate applyDate;
}
