package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.demo.models.ApplyJobs;
import com.example.demo.models.ApplyJobsKey;

@Repository
public interface ApplyJobsRepository
        extends JpaRepository<ApplyJobs, ApplyJobsKey>, JpaSpecificationExecutor<ApplyJobs> {

}
