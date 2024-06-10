package com.example.demo.models;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.AllArgsConstructor;

@Entity
@AllArgsConstructor
public class ApplyJobs {

    @EmbeddedId
    ApplyJobsKey id;

    @ManyToOne
    @MapsId("jobId")
    @JoinColumn(nullable = false, name = "jobId")
    private Job job;

    @ManyToOne
    @MapsId("userOfferingId")
    @JoinColumn(nullable = false, name = "userOfferingId")
    private UserOffering userOffering;

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT (now())")
    private LocalDate applyDate;

}
