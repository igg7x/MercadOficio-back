package com.example.demo.services.specifications;

import org.springframework.data.jpa.domain.Specification;

import com.example.demo.models.ApplyJobs;
import com.example.demo.models.Job;
import com.example.demo.models.User;
import com.example.demo.models.UserOffering;

import jakarta.persistence.criteria.Join;

public class ApplyJobSpecifications {

    public static Specification<ApplyJobs> findByJobIdAndDeletedFalse(String jobId) {

        return (root, query, criteriaBuilder) -> {

            // Join<UserOffering, User> usersJoin = root.join("user");
            // Join<UserOffering, ApplyJobs> joinApplyJobs = root.join("userOffering");
            // Join<ApplyJobs, Job> join = root.join("job");
            // Join<ApplyJobs, Job> rightJoin = root.join("job", JoinType.RIGHT);
            // return criteriaBuilder.and(
            // criteriaBuilder.equal(join.get("jobId"), jobId),
            // criteriaBuilder.equal(rightJoin.get("deleted"), false),
            // criteriaBuilder.isNull((usersJoin.get("deleteAt"))),
            // criteriaBuilder.equal(join.get("isBanned"), false));

            // Unir ApplyJobs con Job
            Join<ApplyJobs, Job> jobJoin = root.join("job");

            // Unir ApplyJobs con UserOffering
            Join<ApplyJobs, UserOffering> userOfferingJoin = root.join("userOffering");

            // Unir UserOffering con User (ahora sí obtenemos los datos del usuario)
            Join<UserOffering, User> userJoin = userOfferingJoin.join("user");

            return criteriaBuilder.and(
                    criteriaBuilder.equal(jobJoin.get("jobId"), jobId), // Filtrar por ID del trabajo
                    criteriaBuilder.equal(jobJoin.get("deleted"), false), // Solo trabajos no eliminados
                    criteriaBuilder.isNull(userJoin.get("deleteAt")), // Solo usuarios que NO estén eliminados
                    criteriaBuilder.equal(userJoin.get("isBanned"), false) // Solo usuarios que NO estén baneados
            );

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
