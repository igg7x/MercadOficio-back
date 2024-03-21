package com.example.demo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.User;
import com.example.demo.models.UserCustomer;

@Repository
public interface UserCustomerRepository extends JpaRepository<UserCustomer, Long> {

    Optional<UserCustomer> findByUser(User user);
}
