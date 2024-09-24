package com.example.demo.models;

import java.util.Date;

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
    private Date createdAt;

    @Column(nullable = true, columnDefinition = "TIMESTAMP")
    private Date deletedAt;

    @Column(nullable = false, columnDefinition = "int default 0")
    private Integer strikesCount;

    @Column(nullable = false, columnDefinition = "tinyint default 0")
    private Boolean isBanned;

    @ManyToOne
    @JoinColumn(name = "userReviewedId", referencedColumnName = "userId", nullable = false)
    private User userReviewed;

    @ManyToOne
    @JoinColumn(name = "userReviewerId", referencedColumnName = "userId", nullable = false)
    private User userReviewer;

    @ManyToOne
    @JoinColumn(name = "jobId", referencedColumnName = "jobId", nullable = false)
    private Job job;

    private Integer rating;

}