package com.example.demo.services.mapper.Job;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import com.example.demo.DTO.Job.CreateJobDTO;
import com.example.demo.DTO.Job.JobDTO;
import com.example.demo.DTO.Job.UpdateJobDTO;
import com.example.demo.DTO.Review.ReviewDTO;
import com.example.demo.models.Category;
import com.example.demo.models.Job;
import com.example.demo.models.UserCustomer;

@Component
public class JobMapperImpl implements JobMapper {

    @Override
    public Job CreateJobDTOtoJob(CreateJobDTO createJobDTO, UserCustomer userCustomer, Category category) {
        Job job = new Job();
        job.setTitle(createJobDTO.getTitle());
        job.setDescription(createJobDTO.getDescription());
        job.setCategory(category);
        job.setUserCustomer(userCustomer);
        job.setDeadline_date(createJobDTO.getDeadline_date());
        job.setPublish_date(LocalDate.now());
        job.setLocation(createJobDTO.getLocation());
        job.setDeleted(false);
        job.setReview("");
        job.setUserOfferingEmail("");
        job.setStatus(false);
        return job;
    }

    @Override
    public JobDTO JobtoJobDTO(Job job) {

        JobDTO jobDTO = new JobDTO();
        jobDTO.setJobId(job.getJobId());
        jobDTO.setTitle(job.getTitle());
        jobDTO.setDescription(job.getDescription());
        jobDTO.setCategory(job.getCategory().getCategoryName());
        jobDTO.setUserCustomerEmail(job.getUserCustomer().getUser().getEmail());
        jobDTO.setLocation(job.getLocation());
        jobDTO.setDeadline_date(job.getDeadline_date());
        jobDTO.setPublish_date(job.getPublish_date());
        jobDTO.setStatus(job.getStatus());
        return jobDTO;
    }

    @Override
    public List<JobDTO> JobListToJobDTOList(List<Job> jobs) {
        return jobs.stream().map(this::JobtoJobDTO).toList();
    }

    @Override
    public List<ReviewDTO> ReviewListToReviewDTOList(List<Job> jobs, String userCustomerEmail) {

        List<ReviewDTO> reviewDTOs = new ArrayList<>();
        for (Job job : jobs) {
            ReviewDTO reviewDTO = new ReviewDTO();
            reviewDTO.setText(job.getReview());
            reviewDTO.setUserEmailReviewer(job.getUserCustomer().getUser().getEmail());
            reviewDTO.setUserEmailReviewed(userCustomerEmail);
            reviewDTOs.add(reviewDTO);
        }

        return reviewDTOs;
    }

    @Override
    public Job UpdateJobDTOtoJob(UpdateJobDTO updateJobDTO, Job job) {

        if (updateJobDTO.getDescription() != null) {
            job.setDescription(updateJobDTO.getDescription());
        }
        if (updateJobDTO.getDeadline_date() != null) {
            job.setDeadline_date(updateJobDTO.getDeadline_date());
        }
        if (updateJobDTO.getUserOfferingEmail() != null) {
            if (job.getUserOfferingEmail() == null || Strings.isEmpty(job.getUserOfferingEmail())) {
                job.setUserOfferingEmail(updateJobDTO.getUserOfferingEmail());
            }
        }
        if (updateJobDTO.getReview() != null && !job.getUserOfferingEmail().isEmpty()) {
            job.setReview(updateJobDTO.getReview());
        }
        return job;
    }
}
