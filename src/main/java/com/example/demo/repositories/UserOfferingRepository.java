package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.UserOffering;

@Repository
public interface UserOfferingRepository extends JpaRepository<UserOffering, Long> {

    // Optional<UserOffering> findByUserId(User userId);

}
