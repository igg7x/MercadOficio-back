package com.example.demo.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "apply_jobs")
public class ApplyJobs {

    @EmbeddedId
    ApplyJobsKey id;

    @ManyToOne
    @MapsId("jobId")
    @JoinColumn(nullable = false, name = "job_id")
    private Job job;

    @ManyToOne
    @MapsId("userOfferingId")
    @JoinColumn(nullable = false, name = "user_offering_id")
    private UserOffering userOffering;

    @Column(nullable = false)
    private LocalDateTime applyDate;

}
