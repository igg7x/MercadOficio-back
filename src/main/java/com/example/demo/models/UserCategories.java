package com.example.demo.models;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;

@Entity
public class UserCategories {

    @EmbeddedId
    UserCategoriesKey id;

    @ManyToOne
    @MapsId("userOfferingId")
    @JoinColumn(name = "userOfferingId")
    UserOffering userOffering;

    @ManyToOne
    @MapsId("categoryId")
    @JoinColumn(name = "categoryId")
    Categories categories;

}
