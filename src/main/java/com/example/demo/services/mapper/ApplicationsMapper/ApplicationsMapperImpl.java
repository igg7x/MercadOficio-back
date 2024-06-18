package com.example.demo.services.mapper.ApplicationsMapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.demo.DTO.Job.ApplyJobDTO;
import com.example.demo.models.ApplyJobs;

@Component
public class ApplicationsMapperImpl implements ApplicationsMapper {

    @Override
    public ApplyJobDTO ApplyJobtoApplyJobDTO(ApplyJobs applyJob) {

        ApplyJobDTO applyJobDTO = new ApplyJobDTO();

        applyJobDTO.setUserOfferingEmail(applyJob.getUserOffering().getUser().getEmail());
        applyJobDTO.setApplyDate(applyJob.getApplyDate());

        return applyJobDTO;
    }

    @Override
    public List<ApplyJobDTO> ApplyJobListToApplyJobDTOList(List<ApplyJobs> applications) {
        return applications.stream().map(this::ApplyJobtoApplyJobDTO).toList();
    }

}
