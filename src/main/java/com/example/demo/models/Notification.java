package com.example.demo.models;

import java.time.LocalDate;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Entity
public class Notification {

    @Id
    @UuidGenerator
    private String notificationId;

    @Column(length = 64, nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "type", joinColumns = @JoinColumn(name = "notificationId"))
    @Column(name = "type", nullable = false)
    private TypesNotification type;

    @Column(length = 128, nullable = false)
    private String message;

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT (now())")
    private LocalDate createdAt;

    // @Column(nullable = false, columnDefinition = "tinyint default 0")
    // private boolean read_status;
    // @ManyToOne
    // @JoinColumn(name = "userId", referencedColumnName = "userId", nullable =
    // false)
    // private User userId;

    // @Column(length = 64, nullable = true)
    // private String jobId;

    public enum TypesNotification {
        SUCCESS,
        INFO,
        WARNING,
        ERROR
    }

    public Notification() {
    }

    public Notification(String title, TypesNotification type, String message) {
        this.title = title;
        this.type = type;
        this.message = message;
        this.createdAt = LocalDate.now();
    }
}
