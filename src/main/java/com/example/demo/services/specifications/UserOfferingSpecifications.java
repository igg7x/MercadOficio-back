package com.example.demo.services.specifications;

import org.springframework.data.jpa.domain.Specification;

import com.example.demo.models.Category;
import com.example.demo.models.User;
import com.example.demo.models.UserOffering;

import jakarta.persistence.criteria.Join;

public class UserOfferingSpecifications {

    public static Specification<UserOffering> filterByCategory(String category) {

        return (root, query, criteriaBuilder) -> {
            Join<UserOffering, Category> join = root.join("userCategories");
            return criteriaBuilder.like(join.get("categoryName"), "%" + category + "%");
        };
    }

    public static Specification<UserOffering> filterByCategory(Category category) {

        return (root, query, criteriaBuilder) -> {
            Join<UserOffering, Category> join = root.join("userCategories");
            return criteriaBuilder.equal(join.get("categoryName"), category.getCategoryName());
        };
    }

    public static Specification<UserOffering> isNotBanned() {

        return (root, query, criteriaBuilder) -> {
            Join<UserOffering, User> join = root.join("user");
            return criteriaBuilder.equal(join.get("isBanned"), false);
        };
    }

    public static Specification<UserOffering> filterByLocation(String location) {
        return (root, query, criteriaBuilder) -> {
            Join<UserOffering, User> join = root.join("user");
            return criteriaBuilder.like(join.get("location"),
                    "%" + location + "%");
        };
    }

    public static Specification<UserOffering> filterByCalification(Integer minCalification, Integer maxCalification) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.between(root.get("calification"), minCalification, maxCalification);
        };
    }
}
