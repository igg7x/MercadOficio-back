package com.example.demo.DTO.Report;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class ReportDTO {
    private String reporterEmail;
    private String reportedEmail;
}
