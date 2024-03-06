package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.UserOffering;

@Repository
public interface UserOfferingRepository extends JpaRepository<UserOffering, Long> {

    // UserOffering findByUserId(User userId);

    // @Query("SELECT uo FROM user_offerings uo WHERE uo.user.userId = :userId")
    // UserOffering findByUserId(@Param("userId") Long userId);
}
