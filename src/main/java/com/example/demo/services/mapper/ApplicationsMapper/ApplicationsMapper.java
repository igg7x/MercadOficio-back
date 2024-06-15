package com.example.demo.services.mapper.ApplicationsMapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.demo.DTO.Job.ApplyJobDTO;
import com.example.demo.models.ApplyJobs;

@Mapper
public interface ApplicationsMapper {

    ApplyJobDTO ApplyJobtoApplyJobDTO(ApplyJobs applyJob);

    List<ApplyJobDTO> ApplyJobListToApplyJobDTOList(List<ApplyJobs> applications);
}
