package com.example.demo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.User.CreateUserDTO;
import com.example.demo.DTO.User.UserDTO;
import com.example.demo.DTO.User.Offering.CreateUserOfferingDTO;
import com.example.demo.DTO.User.Offering.UserOfferingDTO;
import com.example.demo.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{email}")
    private ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @PostMapping
    private ResponseEntity<UserDTO> createUser(@RequestBody CreateUserDTO userDTO) {
        return ResponseEntity.ok(userService.createUser(userDTO));
    }

    @PostMapping("/offering/{email}")
    private ResponseEntity<UserOfferingDTO> createUserOffering(@PathVariable String email,
            @RequestBody CreateUserOfferingDTO userOfferingDTO) {
        return ResponseEntity.ok(userService.createUserOffering(userOfferingDTO, email));
    }

}
