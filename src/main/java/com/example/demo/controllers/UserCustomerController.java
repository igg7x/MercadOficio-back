package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.User.UserDTO;
import com.example.demo.DTO.User.Customer.UserCustomerDTO;
import com.example.demo.auth.CurrentUserEmail;
import com.example.demo.models.UserCustomer;
import com.example.demo.services.UserCustomerService;
import com.example.demo.services.mapper.User.UserMapper;

@RestController
@RequestMapping("/api/v1/users-customers")
@CrossOrigin(origins = "*")
public class UserCustomerController {

    private final UserCustomerService userCustomerService;
    private final UserMapper userMapper;
    @Value("${spring.security.oauth2.resourceserver.jwt.audience}")
    private String audience;

    public UserCustomerController(UserCustomerService userCustomerService, UserMapper userMapper) {
        this.userMapper = userMapper;
        this.userCustomerService = userCustomerService;
    }

    @GetMapping("/private/{email}")
    public ResponseEntity<UserCustomerDTO> getUserCustomer(
            @PathVariable String email) {
        UserCustomer userCustomer = userCustomerService.getUserCustomer(email);
        if (userCustomer == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(userMapper.UserCustomertoUserCustomerDTO(userCustomer));
    }

    @PostMapping("/public/create")
    public ResponseEntity<UserDTO> createUserCustomer(@CurrentUserEmail String email) {
        return ResponseEntity.ok(userCustomerService.createUserCustomer(email));
    }
}
