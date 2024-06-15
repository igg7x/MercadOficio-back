package com.example.demo.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.Job.ApplyJobDTO;
import com.example.demo.services.ApplyJobService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/applications")
@AllArgsConstructor
public class ApplicationsController {

    private final ApplyJobService applyJobsService;

    @PostMapping("/{jobId}")
    public ResponseEntity<Void> applyJob(@PathVariable String jobId, @RequestBody ApplyJobDTO application) {
        try {
            applyJobsService.applyJob(jobId, application);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{jobId}")
    public Page<ApplyJobDTO> getApplicationsByJob(@PathVariable String jobId, Pageable pageable) {
        return applyJobsService.getApplicationsByJob(jobId, pageable);
    }

    @GetMapping("/{userOfferingEmail}")
    public Page<ApplyJobDTO> getApplicationsByUserOffering(@PathVariable String userOfferingEmail, Pageable pageable) {
        return applyJobsService.getApplicationsByUserOffering(userOfferingEmail, pageable);
    }

}
