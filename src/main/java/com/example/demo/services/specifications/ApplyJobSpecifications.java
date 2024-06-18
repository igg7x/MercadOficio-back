package com.example.demo.services.specifications;

import org.springframework.data.jpa.domain.Specification;

import com.example.demo.models.ApplyJobs;
import com.example.demo.models.Job;
import com.example.demo.models.UserOffering;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;

public class ApplyJobSpecifications {

    public static Specification<ApplyJobs> findByJobIdAndDeletedFalseAndStatusFalse(String jobId) {
        return (root, query, criteriaBuilder) -> {
            Join<ApplyJobs, Job> join = root.join("job");
            Join<ApplyJobs, Job> rightJoin = root.join("job", JoinType.RIGHT);
            return criteriaBuilder.and(
                    criteriaBuilder.equal(join.get("jobId"), jobId),
                    criteriaBuilder.equal(rightJoin.get("deleted"), false),
                    criteriaBuilder.equal(rightJoin.get("status"), false));
        };
    }

    public static Specification<ApplyJobs> findByUserOfferingEmailAndDeletedFalseAndStatusFalse(
            Long userOfferingId) {
        return (root, query, criteriaBuilder) -> {
            Join<ApplyJobs, UserOffering> join = root.join("userOffering");

            return criteriaBuilder.and(
                    criteriaBuilder.equal(join.get("userOfferingId"), userOfferingId));
        };
    }

}
