package com.example.demo.services.mapper.Job;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
        if (createJobDTO.getDeadline_date().isAfter(LocalDate.now())) {
            job.setDeadline_date(createJobDTO.getDeadline_date());
        } else {
            throw new IllegalArgumentException("The deadline date is before the publish date");
        }
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
            reviewDTO.setJobId(job.getJobId());
            reviewDTO.setJobTitle(job.getTitle());
            reviewDTO.setText(job.getReview());
            reviewDTO.setUserEmailReviewer(job.getUserCustomer().getUser().getEmail());
            reviewDTO.setUserEmailReviewed(userCustomerEmail);
            reviewDTOs.add(reviewDTO);
        }

        return reviewDTOs;
    }

    @Override
    public Job UpdateJobDTOtoJob(UpdateJobDTO updateJobDTO, Job job) {
        if (!job.getUserOfferingEmail().isEmpty() && job.getStatus()) {

            if (!job.getReview().isEmpty()) {
                throw new IllegalArgumentException("Ya se ha reseñado el trabajo");
            }
            // if (!updateJobDTO.getReview().isEmpty() ||
            // updateJobDTO.getUserOfferingEmail() != null) {
            // job.setReview(updateJobDTO.getReview());
            // }
        } else {
            if (updateJobDTO.getTitle() != null) {
                job.setTitle(updateJobDTO.getTitle());
            }
            if (updateJobDTO.getDescription() != null) {
                job.setDescription(updateJobDTO.getDescription());
            }
            if (updateJobDTO.getDeadline_date() != null) {
                if (!updateJobDTO.getDeadline_date().isBefore(job.getPublish_date())) {
                    job.setDeadline_date(updateJobDTO.getDeadline_date());
                } else {
                    throw new IllegalArgumentException("La fecha límite es anterior a la fecha de publicación");
                }
            }
            if (updateJobDTO.getUserOfferingEmail() != null) {
                job.setUserOfferingEmail(updateJobDTO.getUserOfferingEmail());
                job.setStatus(true);
            }
        }
        return job;
    }
}
