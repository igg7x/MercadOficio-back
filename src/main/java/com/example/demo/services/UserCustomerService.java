package com.example.demo.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.example.demo.models.User;
import com.example.demo.models.UserCustomer;
import com.example.demo.repositories.UserCustomerRepository;

import jakarta.transaction.Transactional;

@Service
public class UserCustomerService {

    private final UserCustomerRepository userCustomerRepository;
    private final UserService userService;

    public UserCustomerService(UserCustomerRepository userCustomerRepository, UserService userService) {
        this.userCustomerRepository = userCustomerRepository;
        this.userService = userService;
    }

    @Transactional
    public UserCustomer createUserCustomer(String userEmail) {
        User user = userService.findByEmail(userEmail);
        if (user == null) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "User not found");
        }

        UserCustomer userCustomer = new UserCustomer();
        userCustomer.setUser(user);
        userCustomerRepository.save(userCustomer);
        return userCustomer;
    }

    public UserCustomer getUserCustomer(String userEmail) {
        User user = userService.findByEmail(userEmail);
        if (user == null) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "User not found");
        }

        UserCustomer userCustomer = userCustomerRepository.findByUser(user).orElse(null);
        if (userCustomer == null) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "User Offering not found");
        }
        return userCustomer;
    }
}
