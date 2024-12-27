package com.example.demo.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.Job.CreateJobDTO;
import com.example.demo.DTO.Job.JobDTO;
import com.example.demo.DTO.Job.UpdateJobDTO;
import com.example.demo.auth.CurrentUserEmail;
import com.example.demo.services.JobService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/v1/jobs")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<JobDTO> getJobById(@PathVariable String id) {
        try {
            return ResponseEntity.ok(jobService.getJobById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/customer")
    public ResponseEntity<Page<JobDTO>> getJobsByUserCustomer(@CurrentUserEmail String email, Pageable pageable) {
        try {
            return ResponseEntity.ok(jobService.getAllActiveJobsByUserCustomerEmail(email, pageable));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<JobDTO> createJob(@Validated @RequestBody CreateJobDTO jobDTO) {
        try {
            return ResponseEntity.ok(jobService.createJob(jobDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/customer/historial")
    public ResponseEntity<Page<JobDTO>> getHistorialJobsByUserCustomer(@CurrentUserEmail String email,
            Pageable pageable) {
        try {
            return ResponseEntity.ok(jobService.getAllHistorialJobsByUserCustomerEmail(email, pageable));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<JobDTO> updateJob(@PathVariable String id, @Validated @RequestBody UpdateJobDTO jobDTO) {
        // try {
        return ResponseEntity.ok(jobService.updateJob(id, jobDTO));
        // } catch (Exception e) {
        // return ResponseEntity.badRequest().build();
        // }
    }

    @PutMapping("/delete/{jobId}")
    public ResponseEntity<JobDTO> deleteJob(@PathVariable String jobId) {
        try {
            jobService.deleteJob(jobId);
            return new ResponseEntity<JobDTO>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<Page<JobDTO>> getAllJobsByCategories(
            Pageable pageable,
            @CurrentUserEmail String userOfferingEmail) {
        try {
            return ResponseEntity.ok(jobService.getAllJobs(pageable, userOfferingEmail));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/offerings/historial")
    public ResponseEntity<Page<JobDTO>> getHistorialJobsByUserOffering(@CurrentUserEmail String email,
            Pageable pageable) {
        try {
            return ResponseEntity.ok(jobService.getAllHistorialJobsByUserOfferingEmail(email, pageable));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
