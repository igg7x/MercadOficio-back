package com.example.demo.controllers;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.User.Offering.CreateUserOfferingDTO;
import com.example.demo.DTO.User.Offering.UpdateUserOfferingDTO;
import com.example.demo.DTO.User.Offering.UserOfferingDTO;
import com.example.demo.DTO.User.Offering.UsersOfferingDTO;
import com.example.demo.services.UserOfferingService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/v1/user-offerings")
public class UserOfferingController {

    private final UserOfferingService userOfferingService;

    public UserOfferingController(UserOfferingService userOfferingService) {
        this.userOfferingService = userOfferingService;
    }

    @GetMapping
    private ResponseEntity<Page<UsersOfferingDTO>> getUsersOffering(
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {

        Page<UsersOfferingDTO> usersOfferingPage = userOfferingService.getUsersOffering(PageRequest.of(page, size));
        return ResponseEntity.ok(usersOfferingPage);
    }

    @PostMapping("search")
    private ResponseEntity<Page<UsersOfferingDTO>> getUsersOfferingByCriteria(
            Pageable pageable,
            @RequestBody Map<String, String> searchCriteria) {
        Page<UsersOfferingDTO> usersOfferingPage = userOfferingService.getUsersOfferingByCriteria(searchCriteria,
                pageable);
        return ResponseEntity.ok(usersOfferingPage);
    }

    @PostMapping("/{email}")
    private ResponseEntity<UserOfferingDTO> createUserOffering(@PathVariable String email,
            @RequestBody CreateUserOfferingDTO userOfferingDTO) {
        try {
            return ResponseEntity.ok(userOfferingService.createUserOffering(userOfferingDTO, email));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{email}")
    private ResponseEntity<UserOfferingDTO> getUserOffering(@PathVariable String email,
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(userOfferingService.getUserOfferingByEmail(email, PageRequest.of(page, size)));
    }

    @PatchMapping("/{email}")
    private ResponseEntity<UserOfferingDTO> updateUserOffering(@PathVariable String email,
            @RequestBody UpdateUserOfferingDTO userOfferingDTO) {
        return ResponseEntity.ok(userOfferingService.updateUserOffering(email,
                userOfferingDTO));
    }
}
