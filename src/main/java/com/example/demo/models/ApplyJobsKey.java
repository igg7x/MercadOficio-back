package com.example.demo.models;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class ApplyJobsKey implements Serializable {

    @Column(name = "jobId")
    Long jobId;

    @Column(name = "userOfferingId")
    Long userOfferingId;
}
