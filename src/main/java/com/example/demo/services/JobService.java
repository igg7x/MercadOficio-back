package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.example.demo.DTO.Categories.CategorieDTO;
import com.example.demo.DTO.Job.CreateJobDTO;
import com.example.demo.DTO.Job.DeleteJobDTO;
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

    public List<JobDTO> getAllJobsByCategories(List<CategorieDTO> categorieDTOs) {
        return jobMapper.JobListToJobDTOList(jobRepository.findAll());
    }

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
    public void deleteJob(DeleteJobDTO deleteJobDTO) {
        Job job = findJobById(deleteJobDTO.getId());
        job.setDeleted(true);
        jobRepository.save(job);
    }

    public Page<ReviewDTO> getReviewsByUserOffering(String userCustomerEmail, Pageable pageable) {
        List<Job> jobs = jobRepository.findByUserOfferingEmail(userCustomerEmail, pageable);
        List<ReviewDTO> reviewDTOs = jobMapper.ReviewListToReviewDTOList(jobs, userCustomerEmail);
        return new PageImpl<>(reviewDTOs, pageable, jobs.size());
    }

    public Page<JobDTO> getAllJobs(List<CategorieDTO> categories, Pageable pageable) {
        List<Category> categoryList = categoryService.getAllCategories(categories);
        List<Job> jobs = new ArrayList<>();
        for (Category category : categoryList) {
            jobs.addAll(jobRepository.findByCategory(category));
        }
        List<JobDTO> jobDTOs = jobMapper.JobListToJobDTOList(jobs);
        return new PageImpl<>(jobDTOs, pageable, jobs.size());
    }

}
