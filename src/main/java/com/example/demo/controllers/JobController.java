package com.example.demo.controllers;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.Categories.CategorieDTO;
import com.example.demo.DTO.Job.CreateJobDTO;
import com.example.demo.DTO.Job.DeleteJobDTO;
import com.example.demo.DTO.Job.JobDTO;
import com.example.demo.DTO.Job.UpdateJobDTO;
import com.example.demo.services.JobService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/v1/jobs")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping("/create")
    public ResponseEntity<JobDTO> createJob(@Validated @RequestBody CreateJobDTO jobDTO) {
        try {
            return ResponseEntity.ok(jobService.createJob(jobDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<JobDTO> getJobById(@PathVariable String id) {
        try {
            return ResponseEntity.ok(jobService.getJobById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<JobDTO> updateJob(@PathVariable String id, @Validated @RequestBody UpdateJobDTO jobDTO) {
        try {
            return ResponseEntity.ok(jobService.updateJob(id, jobDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/delete")
    public ResponseEntity<Void> deleteJob(@RequestBody DeleteJobDTO id) {
        try {
            jobService.deleteJob(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/all")
    public ResponseEntity<Page<JobDTO>> getAllJobs(@RequestBody List<CategorieDTO> categoriesDTOs, Pageable pageable) {
        try {
            return ResponseEntity.ok(jobService.getAllJobs(categoriesDTOs, pageable));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
