package com.example.demo.services.specifications;

import org.springframework.data.jpa.domain.Specification;

import com.example.demo.models.Job;

public class JobSpecifications {

    public static Specification<Job> findByJobIdAndDeletedAtIsNull(String jobId) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.and(
                    criteriaBuilder.equal(root.get("jobId"), jobId),
                    criteriaBuilder.equal(root.get("deleted"), false),
                    criteriaBuilder.equal(root.get("status"), false));
        };
    }

}
