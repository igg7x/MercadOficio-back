package com.example.demo.services.specifications;

import org.springframework.data.jpa.domain.Specification;

import com.example.demo.models.Review;

public class ReviewSpecifications {

    public static Specification<Review> findByUserReviewedIdAndDeletedIsNull(Long userReviewedId) {

        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.and(
                    criteriaBuilder.equal(root.get("userReviewed").get("userId"), userReviewedId),
                    criteriaBuilder.isNull(root.get("deleted_at")));
        };
    }

    public static Specification<Review> findReviews(Long userId, String searchFilter) {

        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.and(
                    criteriaBuilder.equal(root.get(searchFilter).get("userId"), userId),
                    criteriaBuilder.isNull(root.get("deleted_at")));
        };

    }

    public static Specification<Review> findByUserReviewerAndUserReviewedAndJobId(Long userReviewerId,
            Long userReviewedId, String jobId) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.and(
                    criteriaBuilder.equal(root.get("job").get("jobId"), jobId),
                    criteriaBuilder.equal(root.get("userReviewer").get("userId"), userReviewerId),
                    criteriaBuilder.equal(root.get("userReviewed").get("userId"), userReviewedId),
                    criteriaBuilder.isNull(root.get("deleted_at")));

        };
    }
}