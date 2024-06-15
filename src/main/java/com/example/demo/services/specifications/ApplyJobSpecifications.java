package com.example.demo.services.specifications;

import org.springframework.data.jpa.domain.Specification;

import com.example.demo.models.ApplyJobs;
import com.example.demo.models.Job;

import jakarta.persistence.criteria.Join;

public class ApplyJobSpecifications {

    public static Specification<ApplyJobs> findByJobIdAndDeletedFalseAndStatusFalse(String jobId) {
        return (root, query, criteriaBuilder) -> {
            Join<ApplyJobs, Job> join = root.join("job");

            return criteriaBuilder.and(
                    criteriaBuilder.equal(join.get("jobId"), jobId),
                    criteriaBuilder.equal(root.get("deleted"), false),
                    criteriaBuilder.equal(root.get("status"), false));
        };
    }

}
