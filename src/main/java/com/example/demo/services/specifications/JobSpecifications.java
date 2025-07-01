package com.example.demo.services.specifications;

import org.springframework.data.jpa.domain.Specification;

import com.example.demo.models.ApplyJobs;
import com.example.demo.models.Job;
import com.example.demo.models.User;
import com.example.demo.models.UserCustomer;
import com.example.demo.models.UserOffering;

import jakarta.persistence.criteria.Join;

public class JobSpecifications {

    public static Specification<Job> findByJobIdAndDeletedAtIsNull(String jobId) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.and(
                    criteriaBuilder.equal(root.get("jobId"), jobId),
                    criteriaBuilder.equal(root.get("deleted"), false));
        };
    }

    public static Specification<Job> findByCategoryAndStatusIsFalse(String categoryName) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.and(
                    criteriaBuilder.equal(root.get("category").get("categoryName"), categoryName),
                    criteriaBuilder.equal(root.get("deleted"), false));
        };
    }

    // public static Specification<Job> findByCategoriesAndUserOfferingEmail(String
    // userOfferingEmail,
    // Category category) {
    // return (Root<Job> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
    // // Join with the Category table
    // Join<Job, Category> categoryJoin = root.join("category");

    // // Subquery to find job IDs that have been applied for by the given user
    // CriteriaQuery<Long> subquery = builder.createQuery(Long.class);
    // Root<ApplyJobs> applyJobRoot = subquery.from(ApplyJobs.class);
    // Join<ApplyJobs, UserOffering> userOfferingJoin =
    // applyJobRoot.join("userOffering");
    // Join<UserOffering, User> userJoin = userOfferingJoin.join("user");

    // subquery.select(applyJobRoot.get("job").get("id"))
    // .where(builder.equal(userJoin.get("email"), userOfferingEmail));

    // // Main query
    // Predicate categoryPredicate = builder.equal(categoryJoin.get("categoryName"),
    // category.getCategoryName());
    // Predicate jobNotAppliedPredicate = builder.not(root.get("id").in(subquery));

    // return builder.and(categoryPredicate, jobNotAppliedPredicate);
    // };
    // }

    public static Specification<Job> findByUserCustomerEmailAndStatusIsFalse(Long userCustomerId,
            String userOfferingEmail) {
        return (root, query, criteriaBuilder) -> {
            Join<Job, UserCustomer> join = root.join("userCustomer");
            return criteriaBuilder.and(
                    criteriaBuilder.equal(root.get("userOfferingEmail"), userOfferingEmail),
                    criteriaBuilder.equal(root.get("status"), false),
                    criteriaBuilder.equal(join.get("userCustomerId"), userCustomerId),
                    criteriaBuilder.equal(root.get("deleted"), false));
        };
    }

    public static Specification<Job> findByUserCustomerEmailAndStatusIsFalse(Long userCustomerId) {
        return (root, query, criteriaBuilder) -> {

            Join<Job, UserCustomer> join = root.join("userCustomer");
            return criteriaBuilder.and(
                    criteriaBuilder.equal(root.get("status"), false),
                    criteriaBuilder.equal(join.get("userCustomerId"), userCustomerId),
                    criteriaBuilder.equal(root.get("deleted"), false));
        };
    }

    public static Specification<Job> findByUserCustomerEmailAndStatusIsTrue(Long userCustomerId) {
        return (root, query, criteriaBuilder) -> {
            Join<Job, UserCustomer> join = root.join("userCustomer");
            return criteriaBuilder.and(
                    criteriaBuilder.equal(root.get("status"), true),
                    criteriaBuilder.equal(join.get("userCustomerId"), userCustomerId),
                    criteriaBuilder.equal(root.get("deleted"), false));
        };
    }

    public static Specification<Job> findHistorialForUserOffering(String userOfferingEmail) {
        return (root, query, criteriaBuilder) -> {
            // Join entre Job y ApplyJobs
            Join<Job, ApplyJobs> applyJobsJoin = root.join("applyJobs");

            // Join entre ApplyJobs y UserOffering
            Join<ApplyJobs, UserOffering> userOfferingJoin = applyJobsJoin.join("userOffering");

            // Join entre UserOffering y Users para obtener el email
            Join<UserOffering, User> userJoin = userOfferingJoin.join("user");

            // Filtra por el email del usuario oferente
            return criteriaBuilder.and(
                    criteriaBuilder.equal(root.get("deleted"), false),
                    criteriaBuilder.equal(userJoin.get("email"), userOfferingEmail));
        };
    }

    public static Specification<Job> findByStatusIsFalse() {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.and(
                    criteriaBuilder.equal(root.get("status"), false),
                    criteriaBuilder.equal(root.get("deleted"), false));
        };
    }

}
