package com.example.demo.models;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;
    @Column(length = 128, nullable = false)
    private String text;
    @Column(nullable = false, columnDefinition = "integer default 0")
    private Integer num_likes;
    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT (now())")
    private Date createdAt;

    @Column(nullable = true, columnDefinition = "TIMESTAMP")
    private Date deletedAt;

    @ManyToOne
    @JoinColumn(name = "userOfferingId", referencedColumnName = "userOfferingId", nullable = false)
    private UserOffering userOffering;

    @ManyToOne
    @JoinColumn(name = "userCustomerId", referencedColumnName = "userCustomerId", nullable = false)
    private UserCustomer userCustomer;

    @ManyToMany(mappedBy = "userCustomerLikes")
    private List<UserCustomer> reviewLikes;

    @ManyToMany(mappedBy = "userOfferingLikes")
    private List<UserOffering> reviewLikesOffering;

}
