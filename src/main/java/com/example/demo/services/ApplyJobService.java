package com.example.demo.services;

import java.time.LocalDate;

import org.hibernate.mapping.List;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.Job.ApplyJobDTO;
import com.example.demo.models.ApplyJobs;
import com.example.demo.models.ApplyJobsKey;
import com.example.demo.models.Job;
import com.example.demo.models.UserOffering;
import com.example.demo.repositories.ApplyJobsRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ApplyJobService {

    private final JobService jobService;
    private final UserOfferingService userOfferingService;
    private final ApplyJobsRepository applyJobsRepository;

    @Transactional
    public void applyJob(String jobId, String userCustomerEmail) {
        Job job = jobService.findJobById(jobId);
        UserOffering userOffering = userOfferingService.getUserOffering(userCustomerEmail);
        ApplyJobs jobApplication = new ApplyJobs(new ApplyJobsKey(), job, userOffering, LocalDate.now());
        applyJobsRepository.save(jobApplication);
    }

    // public Page<ApplyJobDTO> getApplicationsByJob(String jobId) {

    // List<ApplyJobs> applications = applyJobsRepository.findByJobId(jobId);
    // }

}
