package com.example.demo.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.Job.ApplyJobDTO;
import com.example.demo.DTO.Job.JobDTO;
import com.example.demo.auth.CurrentUserEmail;
import com.example.demo.services.ApplyJobService;

@RestController
@RequestMapping("/api/v1/applications")
@CrossOrigin(origins = "http://localhost:5173")
public class ApplicationsController {

    private final ApplyJobService applyJobsService;

    public ApplicationsController(ApplyJobService applyJobsService) {
        this.applyJobsService = applyJobsService;
    }

    @PostMapping("/{jobId}")
    public ResponseEntity<Void> applyJob(@PathVariable String jobId, @CurrentUserEmail String userOfferingEmail) {
        try {
            applyJobsService.applyJob(jobId, userOfferingEmail);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/job/{jobId}")
    public Page<ApplyJobDTO> getApplicationsByJob(@PathVariable String jobId, Pageable pageable) {
        return applyJobsService.getApplicationsByJob(jobId, pageable);
    }

    @GetMapping("/get")
    public Page<JobDTO> getApplicationsByUserOffering(@CurrentUserEmail String userOfferingEmail, Pageable pageable) {
        return applyJobsService.getApplicationsByUserOffering(userOfferingEmail, pageable);
    }

}
