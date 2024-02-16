package com.example.demo.models;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;
    @Column(length = 128, nullable = false)
    private String text;
    @Column(nullable = false)
    private Integer num_likes;
    @Column(nullable = false)
    private Date dateReview;

    @JoinColumn(name = "userOfferingId", referencedColumnName = "userOfferingId", nullable = false)
    private UserOffering userOffering;

    @JoinColumn(name = "userCustomerId", referencedColumnName = "userCustomerId", nullable = false)
    private UserCustomer userCustomer;

}
