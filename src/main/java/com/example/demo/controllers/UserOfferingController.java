package com.example.demo.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.User.Offering.CreateUserOfferingDTO;
import com.example.demo.DTO.User.Offering.UpdateUserOfferingDTO;
import com.example.demo.DTO.User.Offering.UserOfferingDTO;
import com.example.demo.DTO.User.Offering.UsersOfferingDTO;
import com.example.demo.services.UserOfferingService;

@RestController
@RequestMapping("/api/v1/user-offerings")
public class UserOfferingController {

    private final UserOfferingService userOfferingService;

    public UserOfferingController(UserOfferingService userOfferingService) {
        this.userOfferingService = userOfferingService;
    }

    @GetMapping
    private ResponseEntity<List<UsersOfferingDTO>> getUsersOffering() {
        List<UsersOfferingDTO> usersOffering = userOfferingService.getUsersOffering();
        return ResponseEntity.ok(usersOffering);
    }

    @PostMapping("/{email}")
    private ResponseEntity<UserOfferingDTO> createUserOffering(@PathVariable String email,
            @RequestBody CreateUserOfferingDTO userOfferingDTO) {
        // try {
        return ResponseEntity.ok(userOfferingService.createUserOffering(userOfferingDTO, email));
        // } catch (Exception e) {
        // return ResponseEntity.badRequest().build();
        // }
    }

    @GetMapping("/{email}")
    private ResponseEntity<UserOfferingDTO> getUserOffering(@PathVariable String email) {
        return ResponseEntity.ok(userOfferingService.getUserOfferingByEmail(email));
    }

    // @PatchMapping("/{email}")
    // private ResponseEntity<UserOfferingDTO> updateUserOffering(@PathVariable
    // String email,
    // @RequestBody UpdateUserOfferingDTO userOfferingDTO) {

    // return ResponseEntity.ok(userOfferingService.updateUserOffering(email,
    // userOfferingDTO));
    // }
}
