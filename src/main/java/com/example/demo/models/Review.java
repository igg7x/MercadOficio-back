package com.example.demo.models;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "reviews")
@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @Column(length = 128, nullable = false)
    private String text;

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT (now())")
    private LocalDate created_at;

    @Column(nullable = true, columnDefinition = "TIMESTAMP")
    private LocalDate deleted_at;

    @ManyToOne
    @JoinColumn(name = "userReviewedId", referencedColumnName = "userId", nullable = false)
    private User userReviewed;

    @ManyToOne
    @JoinColumn(name = "userReviewerId", referencedColumnName = "userId", nullable = false)
    private User userReviewer;

    @ManyToOne
    @JoinColumn(name = "jobId", referencedColumnName = "jobId", nullable = false)
    private Job job;

    @Column(nullable = true, columnDefinition = "double default 0")
    private Integer rating;

}