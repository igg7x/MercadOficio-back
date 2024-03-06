package com.example.demo.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.User.CreateUserDTO;
import com.example.demo.DTO.User.UpdateUserDTO;
import com.example.demo.DTO.User.UserDTO;
import com.example.demo.DTO.User.Offering.CreateUserOfferingDTO;
import com.example.demo.DTO.User.Offering.UserOfferingDTO;
import com.example.demo.DTO.User.Offering.UsersOfferingDTO;
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
        try {
            return ResponseEntity.ok(userService.getUserByEmail(email));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/offerings")
    private ResponseEntity<List<UsersOfferingDTO>> getUsersOffering() {
        List<UsersOfferingDTO> usersOffering = userService.getUsersOffering();
        return ResponseEntity.ok(usersOffering);
    }

    @PostMapping
    private ResponseEntity<UserDTO> createUser(@RequestBody CreateUserDTO userDTO) {

        try {
            return ResponseEntity.ok(userService.createUser(userDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/offering/{email}")
    private ResponseEntity<UserOfferingDTO> createUserOffering(@PathVariable String email,
            @RequestBody CreateUserOfferingDTO userOfferingDTO) {
        try {
            return ResponseEntity.ok(userService.createUserOffering(userOfferingDTO, email));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/offering/{email}")
    private ResponseEntity<UserDTO> updateUser(@PathVariable String email, @RequestBody UpdateUserDTO userDTO) {
        try {
            return ResponseEntity.ok(userService.updateUser(email, userDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

    }

    @PutMapping("/doffering/{email}")
    private ResponseEntity<Void> deleteUser(@PathVariable String email) {
        try {
            userService.deleteUser(email);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
