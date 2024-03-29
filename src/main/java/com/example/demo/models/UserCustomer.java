package com.example.demo.models;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "userCustomers")
public class UserCustomer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userCustomerId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;

    @OneToMany(mappedBy = "userCustomer")
    private List<Review> reviews;

    @ManyToMany()
    @JoinTable(name = "customerReviewsLikes", joinColumns = @JoinColumn(name = "userCustomerId"), inverseJoinColumns = @JoinColumn(name = "reviewId"), uniqueConstraints = @UniqueConstraint(columnNames = {
            "userCustomerId", "reviewId" }))
    private List<Review> userCustomerLikes;

}
