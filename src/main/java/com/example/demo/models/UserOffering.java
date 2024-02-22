package com.example.demo.models;

import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "userOfferings")
@Data
public class UserOffering {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userOfferingId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;

    @Column(length = 64, nullable = false)
    private String location;

    @Column(nullable = false)
    private Integer experience;

    @Column(nullable = false)
    private Integer mark;
    @Column(nullable = false)
    private Integer price; // this is price per hour or per day

    private Date workDayStart;
    private Date workDayEnd;
    
    @OneToMany(mappedBy = "userOffering")
    private List<Review> reviews;

    @ManyToMany()
    @JoinTable(name = "offeringReviewsLikes", joinColumns = @JoinColumn(name = "userOfferingId"), inverseJoinColumns = @JoinColumn(name = "reviewId"))
    private List<Review> userOfferingLikes;

    @OneToMany(mappedBy = "userOffering")
    private List<UserCategories> userCategories;

}
