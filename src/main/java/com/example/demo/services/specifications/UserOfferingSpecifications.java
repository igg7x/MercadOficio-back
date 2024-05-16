package com.example.demo.services.specifications;

import org.springframework.data.jpa.domain.Specification;

import com.example.demo.models.Category;
import com.example.demo.models.UserOffering;

import jakarta.persistence.criteria.Join;

public class UserOfferingSpecifications {

    public static Specification<UserOffering> filterByCategory(String category) {

        return (root, query, criteriaBuilder) -> {
            Join<UserOffering, Category> join = root.join("userCategories");
            return criteriaBuilder.like(join.get("categoryName"), "%" + category + "%");
        };
    }

    public static Specification<UserOffering> filterByLocation(String location) {

        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.like(root.get("location"),
                    "%" + location + "%");
            // return criteriaBuilder.equal(root.get("location"), location);
        };
    }

    public static Specification<UserOffering> filterByCalification(Integer minCalification, Integer maxCalification) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.between(root.get("price"), minCalification, maxCalification);
        };
    }
}
