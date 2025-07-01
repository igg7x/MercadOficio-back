package com.example.demo.services.mapper.Job;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.demo.DTO.Job.CreateJobDTO;
import com.example.demo.DTO.Job.JobDTO;
import com.example.demo.DTO.Job.UpdateJobDTO;
import com.example.demo.DTO.Review.ReviewDTO;
import com.example.demo.models.Category;
import com.example.demo.models.Job;
import com.example.demo.models.UserCustomer;

@Mapper
public interface JobMapper {

    Job CreateJobDTOtoJob(CreateJobDTO createJobDTO, UserCustomer userCustomer, Category category);

    JobDTO JobtoJobDTO(Job job);

    List<JobDTO> JobListToJobDTOList(List<Job> all);

    List<ReviewDTO> ReviewListToReviewDTOList(List<Job> jobs, String userCustomerEmail);

    Job UpdateJobDTOtoJob(UpdateJobDTO updateJobDTO, Job job);

}
