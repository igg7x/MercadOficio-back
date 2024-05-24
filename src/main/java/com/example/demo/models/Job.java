package com.example.demo.models;

import java.sql.Date;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "jobs")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jobId;

    @Column(length = 64, nullable = false)
    private String title;

    @Column(length = 128, nullable = false)
    private String description;

    @Column(length = 64, nullable = false)
    private String location;

    @Column(nullable = true, columnDefinition = "tinyint default 0")
    private Boolean status;

    @Column(nullable = false, columnDefinition = "varchar(128) default ''")
    private String review;

    @Column(nullable = false)
    private Date publish_date;

    @Column(nullable = false)
    private Date deadline_date;

    @OneToOne
    @JoinColumn(name = "categoryId", referencedColumnName = "categoryId", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "userCustomerId", referencedColumnName = "userCustomerId", nullable = false)
    private UserCustomer userCustomer;

    @OneToMany(mappedBy = "job")
    private Set<ApplyJobs> applyJobs;

}