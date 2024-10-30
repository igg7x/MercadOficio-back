package com.example.demo.services.specifications;

import org.springframework.data.jpa.domain.Specification;

import com.example.demo.models.Notification;
import com.example.demo.models.NotificationsUsers;
import com.example.demo.models.User;

import jakarta.persistence.criteria.Join;

public class NotificationsSpecifications {

    public static Specification<NotificationsUsers> getNotificationsByUserId(Long userId) {

        return (root, query, criteriaBuilder) -> {
            Join<NotificationsUsers, Notification> notificationJoin = root.join("notification");
            Join<NotificationsUsers, User> userJoin = root.join("user");
            return criteriaBuilder.and(
                    criteriaBuilder.equal(userJoin.get("userId"), userId),
                    criteriaBuilder.equal(root.get("read_status"), false));
        };
    }

}
