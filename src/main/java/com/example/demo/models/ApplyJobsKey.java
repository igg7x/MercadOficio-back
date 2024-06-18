package com.example.demo.models;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class ApplyJobsKey implements Serializable {

    public ApplyJobsKey() {
    }

    public ApplyJobsKey(String jobId, Long userOfferingId) {
        this.jobId = jobId;
        this.userOfferingId = userOfferingId;
    }

    @Column(name = "job_id")
    String jobId;

    @Column(name = "user_offering_id")
    Long userOfferingId;
}
