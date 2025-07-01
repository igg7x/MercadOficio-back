package com.example.demo.models.utils;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class NotificationsUsersKey implements Serializable {

    public NotificationsUsersKey() {
    }

    public NotificationsUsersKey(String notificationId, Long userId) {
        this.notificationId = notificationId;
        this.userId = userId;
    }

    @Column(name = "notification_id")
    private String notificationId;

    @Column(name = "user_id")
    private Long userId;

}
