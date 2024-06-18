package com.example.demo.models;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
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

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT (now())")
    private LocalDate applyDate;

}
