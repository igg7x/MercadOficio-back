package com.example.demo.models;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.example.demo.auth.Roles;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;

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

    @Column(length = 128, nullable = true)
    private String picture;

    @Column(length = 64, nullable = true)
    private String location;

    @Column(length = 32, nullable = true)
    private Long phone;

    // @Column(length = 64, nullable = true)
    private LocalDateTime deleteAt;

    @Column(length = 128, nullable = true)
    private String biography;

    @OneToOne(mappedBy = "user")
    private UserOffering userOffering;

    @JsonIgnore
    @OneToOne(mappedBy = "user")
    private UserCustomer userCustomer;

    @Column(nullable = false)
    private Integer strikesCount = 0;

    @Column(nullable = false)
    private Boolean isBanned = false;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user-roles", joinColumns = @JoinColumn(name = "userId"))
    @Column(name = "roles")
    private Set<Roles> roles = new HashSet<>();

}
