package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.example.demo.DTO.Job.CreateJobDTO;
import com.example.demo.DTO.Job.JobDTO;
import com.example.demo.DTO.Job.UpdateJobDTO;
import com.example.demo.DTO.Review.ReviewDTO;
import com.example.demo.models.Category;
import com.example.demo.models.Job;
import com.example.demo.models.UserCustomer;
import com.example.demo.repositories.JobRepository;
import com.example.demo.services.mapper.Job.JobMapper;
import com.example.demo.services.specifications.JobSpecifications;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class JobService {
    private final JobRepository jobRepository;
    private final UserCustomerService userCustomerService;
    private final CategoryService categoryService;
    private final JobMapper jobMapper;
    private final UserOfferingService userOfferingService;

    public Job findJobById(String id) {
        Job job = jobRepository.findOne(JobSpecifications.findByJobIdAndDeletedAtIsNull(id)).orElse(null);
        if (job == null) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Job not found");
        }
        return job;
    }

    public JobDTO getJobById(String id) {
        Job job = findJobById(id);
        return jobMapper.JobtoJobDTO(job);
    }

    @Transactional
    public JobDTO createJob(CreateJobDTO createJobDTO) {
        UserCustomer userCustomer = userCustomerService.getUserCustomer(createJobDTO.getUserCustomerEmail());
        Category category = categoryService.getCategory(createJobDTO.getCategory());
        Job job = jobMapper.CreateJobDTOtoJob(createJobDTO, userCustomer, category);
        Job jobCreated = jobRepository.save(job);
        return jobMapper.JobtoJobDTO(jobCreated);
    }

    @Transactional
    public JobDTO updateJob(String jobId, UpdateJobDTO updateJobDTO) {
        Job job = findJobById(jobId);
        Job jobToUpdate = jobMapper.UpdateJobDTOtoJob(updateJobDTO, job);
        Job jobUpdated = jobRepository.save(jobToUpdate);
        return jobMapper.JobtoJobDTO(jobUpdated);
    }

    @Transactional
    public JobDTO deleteJob(String jobId) {
        Job job = findJobById(jobId);
        job.setDeleted(true);
        jobRepository.save(job);
        return jobMapper.JobtoJobDTO(job);
    }

    public Page<ReviewDTO> getReviewsByUserOffering(String userOfferingEmail, Pageable pageable) {
        List<Job> jobs = jobRepository.findByUserOfferingEmailAndReviewIsNotNull(userOfferingEmail);
        List<ReviewDTO> reviewDTOs = jobMapper.ReviewListToReviewDTOList(jobs, userOfferingEmail);
        return new PageImpl<>(reviewDTOs, pageable, jobs.size());
    }

    // Obtengo todos los JOBS que tengan las categorias que se pasan por parametro y
    // esten disponibles
    // (status = false) y no esten eliminados (deleted = false)

    public Page<JobDTO> getAllJobs(Pageable pageable, String userOfferingEmail) {
        List<Category> categoryList = userOfferingService.getUserOffering(userOfferingEmail).getUserCategories();
        List<Job> jobs = new ArrayList<>();
        for (Category category : categoryList) {
            jobs.addAll(
                    jobRepository.findByCategoriesAndUserOfferingEmail(userOfferingEmail, category.getCategoryName()));
        }
        Page<JobDTO> jobDTOsPage = new PageImpl<>(jobMapper.JobListToJobDTOList(jobs), pageable, jobs.size());
        return jobDTOsPage;

    }

    public Page<JobDTO> getAllHistorialJobsByUserCustomerEmail(String userCustomerEmail, Pageable pageable) {
        UserCustomer userCustomer = userCustomerService.getUserCustomer(userCustomerEmail);
        Page<Job> jobsPage = jobRepository
                .findAll(JobSpecifications.findByUserCustomerEmailAndStatusIsTrue(userCustomer.getUserCustomerId()),
                        pageable);
        Page<JobDTO> jobDTOsPage = jobsPage.map(jobMapper::JobtoJobDTO);

        return jobDTOsPage;
    }

    public Page<JobDTO> getAllActiveJobsByUserCustomerEmail(String userCustomerEmail, Pageable pageable) {
        UserCustomer userCustomer = userCustomerService.getUserCustomer(userCustomerEmail);
        Page<Job> jobsPage = jobRepository
                .findAll(
                        JobSpecifications.findByUserCustomerEmailAndStatusIsFalse(
                                userCustomer.getUserCustomerId()),
                        pageable);
        Page<JobDTO> jobDTOsPage = jobsPage.map(jobMapper::JobtoJobDTO);

        return jobDTOsPage;
    }

    public Page<JobDTO> getAllHistorialJobsByUserOfferingEmail(String userOfferingEmail, Pageable pageable) {
        Page<Job> jobsPage = jobRepository
                .findAll(JobSpecifications.findHistorialForUserOffering(userOfferingEmail), pageable);
        // List<Job> jobsList = jobRepository
        // .findHistorialJobsApplicationsByUserOfferingEmail(userOfferingEmail);
        // Page<Job> jobsPage = new PageImpl<>(jobsList, pageable, jobsList.size());

        Page<JobDTO> jobDTOsPage = jobsPage.map(jobMapper::JobtoJobDTO);
        return jobDTOsPage;
    }
}
