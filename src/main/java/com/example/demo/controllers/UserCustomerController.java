package com.example.demo.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user-customers")
public class UserCustomerController {

    private final UserCustomerService userCustomerService;

    public UserCustomerController(UserCustomerService userCustomerService) {
        this.userCustomerService = userCustomerService;
    }

    @PostMapping("/{email}")
    public UserDTO createUserCustomer(@PathVariable String email) {
        return userCustomerService.createUserCustomer(email);
    }
}
