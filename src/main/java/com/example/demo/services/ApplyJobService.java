package com.example.demo.services;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.example.demo.DTO.Job.ApplyJobDTO;
import com.example.demo.models.ApplyJobs;
import com.example.demo.models.ApplyJobsKey;
import com.example.demo.models.Job;
import com.example.demo.models.UserOffering;
import com.example.demo.repositories.ApplyJobsRepository;
import com.example.demo.services.mapper.ApplicationsMapper.ApplicationsMapper;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ApplyJobService {

    private final JobService jobService;
    private final UserOfferingService userOfferingService;
    private final ApplyJobsRepository applyJobsRepository;
    private final ApplicationsMapper applicationsMapper;

    @Transactional
    public void applyJob(String jobId, ApplyJobDTO applyJobDTO) {
        Job job = jobService.findJobById(jobId);
        UserOffering userOffering = userOfferingService.getUserOffering(applyJobDTO.getUserOfferingEmail());
        if (!userOffering.getUserCategories().contains(job.getCategory())) {
            throw new HttpClientErrorException(org.springframework.http.HttpStatus.BAD_REQUEST,
                    "User Offering does not have the required category");
        }
        ApplyJobs jobApplication = new ApplyJobs(new ApplyJobsKey(), job, userOffering, applyJobDTO.getApplyDate());
        applyJobsRepository.save(jobApplication);
    }

    public Page<ApplyJobDTO> getApplicationsByJob(String jobId, Pageable pageable) {

        List<ApplyJobs> applications = applyJobsRepository.findByJobId(jobId);
        List<ApplyJobDTO> applicationsDTO = applicationsMapper.ApplyJobListToApplyJobDTOList(applications);
        return new PageImpl<>(applicationsDTO, pageable, applications.size());
    }

    public Page<ApplyJobDTO> getApplicationsByUserOffering(String userCustomerEmail, Pageable pageable) {
        List<ApplyJobs> applications = applyJobsRepository.findByUserCustomerEmail(userCustomerEmail);
        List<ApplyJobDTO> applicationsDTO = applicationsMapper.ApplyJobListToApplyJobDTOList(applications);
        return new PageImpl<>(applicationsDTO, pageable, applications.size());
    }

}
