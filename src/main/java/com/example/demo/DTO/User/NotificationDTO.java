package com.example.demo.DTO.User;

import java.time.LocalDate;

import com.example.demo.models.Notification;
import com.example.demo.models.NotificationsUsers;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class NotificationDTO {

    public NotificationDTO(Notification notification, NotificationsUsers notificationsUsers) {
        this.id = notification.getNotificationId();
        this.title = notification.getTitle();
        this.type = notification.getType().toString();
        this.message = notification.getMessage();
        this.read_status = notificationsUsers.isRead_status();
        this.createdAt = notification.getCreatedAt();
        this.jobId = notificationsUsers.getJobId();
    }

    private String id;
    private String title;
    private String type;
    private String message;
    private boolean read_status;
    private LocalDate createdAt;
    private String jobId;

}
