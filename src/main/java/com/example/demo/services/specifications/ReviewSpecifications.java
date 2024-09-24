package com.example.demo.services.specifications;

import org.springframework.data.jpa.domain.Specification;

import com.example.demo.models.Review;

public class ReviewSpecifications {

    public static Specification<Review> findByUserReviewedIdAndDeletedIsNull(Long userReviewedId) {

        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.and(
                    criteriaBuilder.equal(root.get("userReviewed").get("userId"), userReviewedId),
                    criteriaBuilder.isNull(root.get("deletedAt")));
        };
    }
}

// userReviewed
// userReviewedId