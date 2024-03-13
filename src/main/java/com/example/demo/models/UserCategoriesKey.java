package com.example.demo.models;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Setter;

@Embeddable
@Setter

public class UserCategoriesKey implements Serializable {

    public UserCategoriesKey(Long userOfferingId, Long categoryId) {
        this.userOfferingId = userOfferingId;
        this.categoryId = categoryId;
    }

    @Column(name = "userOfferingId")
    Long userOfferingId;
    @Column(name = "categoryId")
    Long categoryId;

}
