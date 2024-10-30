package com.example.demo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "reports")
@AllArgsConstructor
@NoArgsConstructor
public class Report {

    public Report(User user, User userReported) {
        this.reporterUserId = user;
        this.reportedUserId = userReported;
    }

    @Id
    @GeneratedValue
    private Long reportId;

    @ManyToOne
    @JoinColumn(name = "reporterUserId", referencedColumnName = "userId", nullable = false)
    private User reporterUserId;

    @ManyToOne
    @JoinColumn(name = "reportedUserId", referencedColumnName = "userId", nullable = false)
    private User reportedUserId;
}
