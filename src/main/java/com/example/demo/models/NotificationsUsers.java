package com.example.demo.models;

import com.example.demo.models.utils.NotificationsUsersKey;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "notifications_users")
public class NotificationsUsers {

    @EmbeddedId
    NotificationsUsersKey id;

    @ManyToOne
    @MapsId("notificationId")
    @JoinColumn(nullable = false, name = "notification_id")
    private Notification notification;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    @Column(length = 64, nullable = true, columnDefinition = "varchar(64) default 'N/A'")
    private String jobId;

    @Column(nullable = false)
    private boolean read_status = false;

    @Column(nullable = false)
    private boolean deleted = false;

    public NotificationsUsers(Notification notification, User user, String jobId) {
        this.notification = notification;
        this.user = user;
        this.jobId = jobId;
        this.id = new NotificationsUsersKey(notification.getNotificationId(), user.getUserId());
        this.read_status = false;
        this.deleted = false;
    }

}
