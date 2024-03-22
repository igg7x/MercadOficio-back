package com.example.demo.models;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "users")
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(length = 32, nullable = false)
    private String name;
    @Column(name = "surname", length = 32, nullable = false)
    private String surname;
    @Column(length = 32, nullable = false, unique = true)
    private String email;
    @Column(length = 32, nullable = false)
    private String password;
    @Column(length = 64, nullable = true)
    private Date deleteAt;
    @Column(length = 128, nullable = true)
    private String biography;

    @OneToOne(mappedBy = "user")
    private UserOffering userOffering;

    @OneToOne(mappedBy = "user")
    private UserCustomer userCustomer;
}
