package com.example.demo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.demo.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    Optional<User> findByUserIdAndDeleteAtIsNull(Long id);

    List<User> findByDeleteAtIsNull();

    Optional<User> findByEmailAndDeleteAtIsNullAndIsBannedIsFalse(String email);

    Optional<User> findByEmailAndDeleteAtIsNull(String email);

    Optional<User> findByEmail(String email);

    List<User> findByEmailIn(List<String> usersEmailsToNotify);

    long countByIsBannedIsFalseAndDeleteAtIsNull();

    long countBydeleteAtIsNull();

}
