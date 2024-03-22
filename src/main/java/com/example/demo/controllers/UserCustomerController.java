package com.example.demo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.User.UserDTO;
import com.example.demo.services.UserCustomerService;

@RestController
@RequestMapping("/api/v1/user-customers")
public class UserCustomerController {

    private final UserCustomerService userCustomerService;

    public UserCustomerController(UserCustomerService userCustomerService) {
        this.userCustomerService = userCustomerService;
    }

    @PostMapping("/{email}")
    public ResponseEntity<UserDTO> createUserCustomer(@PathVariable String email) {
        return ResponseEntity.ok(userCustomerService.createUserCustomer(email));
    }
}
