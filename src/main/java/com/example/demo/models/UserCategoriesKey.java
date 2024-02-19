package com.example.demo.models;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class UserCategoriesKey implements Serializable {

    @Column(name = "userOfferingId")
    Long userOfferingId;
    @Column(name = "categoryId")
    Long categoryId;

}
