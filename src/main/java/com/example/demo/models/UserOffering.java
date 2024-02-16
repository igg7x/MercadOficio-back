package com.example.demo.models;

import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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

    @Column(length = 128, nullable = false)
    private String biography;
    @Column(length = 64, nullable = false)
    private String location;

    @Column(nullable = false)
    private Integer experience;

    @Column(nullable = false)
    private Integer mark;
    @Column(nullable = false)
    private Integer price;

    private Date schedules;
}
