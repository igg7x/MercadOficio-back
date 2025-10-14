package com.example.demo.models;

// import com.example.demo.models.utils.ReportReasonEnum;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

    public Report(User user, User userReported, Boolean reportStatus, ReportReasonEnum reportReason) {
        this.reporterUserId = user;
        this.reportedUserId = userReported;
        this.reportStatus = reportStatus;
        this.reportReason = reportReason;
    }

    public enum ReportReasonEnum {
        SPAM,
        INAPPROPRIATE_CONTENT,
        SCAM,
        OTHER;
    }

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long reportId;

    @ManyToOne
    @JoinColumn(name = "reporterUserId", referencedColumnName = "userId", nullable = false)
    private User reporterUserId;

    @Column(nullable = false)
    private Boolean reportStatus = true; // the value 1 indicates that the report is active, and 0 indicates that it is
    // inactive

    @ManyToOne
    @JoinColumn(name = "reportedUserId", referencedColumnName = "userId", nullable = false)
    private User reportedUserId;

    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "reason", joinColumns = @JoinColumn(name = "reportId"))
    @Column(name = "reason", nullable = false)
    private ReportReasonEnum reportReason;

}
