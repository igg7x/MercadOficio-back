package com.example.demo.services;

import org.springframework.stereotype.Service;

import com.example.demo.models.Report;
import com.example.demo.repositories.ReportRepository;
import com.example.demo.services.specifications.ReportSpecifications;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;

    public Report getReportIfExist(Long reporterId, Long reporteredId) {
        return reportRepository.findOne(ReportSpecifications.findByReporterIdAndReporteredId(reporterId, reporteredId))
                .orElse(null);
    }

    @Transactional
    public Report createReport(Report report) {
        return reportRepository.save(report);
    }

}