package com.example.demo.models;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(length = 32, nullable = false)
    private String name;
    @Column(length = 32, nullable = false)
    private String surname;

    @Column(length = 32, nullable = false, unique = true)

    private String email;
    @Column(length = 32, nullable = false)
    private String password;
    @Column(length = 64, nullable = true)
    private Date deleteDate;
    @Column(length = 128, nullable = true)
    private String biography;

    private String image;

    @OneToOne(mappedBy = "user")
    private UserOffering userOffering;

    @OneToOne(mappedBy = "user")
    private UserCustomer userCustomer;
}
