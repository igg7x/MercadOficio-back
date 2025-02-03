package com.example.demo.services.specifications;

import org.springframework.data.jpa.domain.Specification;

import com.example.demo.models.User;

public class UserSpecifications {

    public static Specification<User> getUsers() {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.isNull(root.get("deleteAt"));
        };
    }
}
