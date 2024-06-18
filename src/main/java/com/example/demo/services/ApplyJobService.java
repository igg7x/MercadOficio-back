package com.example.demo.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.example.demo.DTO.Job.ApplyJobDTO;
import com.example.demo.DTO.Job.JobDTO;
import com.example.demo.models.ApplyJobs;
import com.example.demo.models.ApplyJobsKey;
import com.example.demo.models.Job;
import com.example.demo.models.UserOffering;
import com.example.demo.repositories.ApplyJobsRepository;
import com.example.demo.services.mapper.ApplicationsMapper.ApplicationsMapper;
import com.example.demo.services.mapper.Job.JobMapper;
import com.example.demo.services.specifications.ApplyJobSpecifications;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ApplyJobService {

    private final JobService jobService;
    private final UserOfferingService userOfferingService;
    private final ApplyJobsRepository applyJobsRepository;
    private final ApplicationsMapper applicationsMapper;
    private final JobMapper jobMapper;

    @Transactional
    public void applyJob(String jobId, ApplyJobDTO applyJobDTO) {
        Job job = jobService.findJobById(jobId);
        UserOffering userOffering = userOfferingService.getUserOffering(applyJobDTO.getUserOfferingEmail());
        if (!userOffering.getUserCategories().contains(job.getCategory())) {
            throw new HttpClientErrorException(org.springframework.http.HttpStatus.BAD_REQUEST,
                    "User Offering does not have the required category");
        }

        ApplyJobsKey applyJobsKey = new ApplyJobsKey(job.getJobId(), userOffering.getUserOfferingId());
        ApplyJobs newJobApplication = new ApplyJobs();
        newJobApplication.setId(applyJobsKey);
        newJobApplication.setJob(job);
        newJobApplication.setUserOffering(userOffering);
        newJobApplication.setApplyDate(LocalDate.now());
        applyJobsRepository.save(newJobApplication);
    }

    public Page<ApplyJobDTO> getApplicationsByJob(String jobId, Pageable pageable) {

        List<ApplyJobs> applications = applyJobsRepository
                .findAll(ApplyJobSpecifications.findByJobIdAndDeletedFalseAndStatusFalse(jobId));
        List<ApplyJobDTO> applicationsDTO = applicationsMapper.ApplyJobListToApplyJobDTOList(applications);
        return new PageImpl<>(applicationsDTO, pageable, applications.size());
    }

    public Page<JobDTO> getApplicationsByUserOffering(String userCustomerEmail, Pageable pageable) {
        UserOffering userOffering = userOfferingService.getUserOffering(userCustomerEmail);
        List<ApplyJobs> applications = applyJobsRepository.findAll(
                ApplyJobSpecifications
                        .findByUserOfferingEmailAndDeletedFalseAndStatusFalse(userOffering.getUserOfferingId()));
        List<Job> jobs = new ArrayList<>();
        for (ApplyJobs application : applications) {
            jobs.add(application.getJob());
        }

        return new PageImpl<>(jobMapper.JobListToJobDTOList(jobs), pageable, applications.size());
    }

}
