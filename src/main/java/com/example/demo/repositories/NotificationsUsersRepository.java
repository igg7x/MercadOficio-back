package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.demo.models.NotificationsUsers;
import com.example.demo.models.utils.NotificationsUsersKey;

@Repository
public interface NotificationsUsersRepository
        extends JpaRepository<NotificationsUsers, NotificationsUsersKey>, JpaSpecificationExecutor<NotificationsUsers> {

}
