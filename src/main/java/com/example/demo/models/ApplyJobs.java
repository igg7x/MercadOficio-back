package com.example.demo.models;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;

@Entity
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
    private Date applyDate;

}
