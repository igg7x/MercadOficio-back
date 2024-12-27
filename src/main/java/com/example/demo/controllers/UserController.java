package com.example.demo.controllers;

import java.security.Principal;
import java.util.Collections;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.User.CreateUserDTO;
import com.example.demo.DTO.User.UpdateUserDTO;
import com.example.demo.DTO.User.UserDTO;
import com.example.demo.Exceptions.UserNotFoundException;
import com.example.demo.auth.CurrentUserEmail;
import com.example.demo.models.User;

import com.example.demo.services.UserService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@EnableMethodSecurity
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/private/{email}")
    private ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email, Principal principal) {
        try {
            UserDTO user = userService.getUserByEmail(email);

            // if (user != null) {
            // Notification noti = notificationService.createNotification(
            // "Se ha consultado tu perfil",
            // TypesNotification.INFO,
            // "Tu perfil ha sido consultado por otro usuario",
            // user.getEmail());

            // // notificationService.sendNotification(noti);
            // // notificationService.sendPrivateNotification(noti, user.getEmail());
            // return ResponseEntity.ok(user);
            // } else {
            // return ResponseEntity.notFound().build();
            // }
            return ResponseEntity.ok(user);
        } catch (

        Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/public/create")
    private ResponseEntity<UserDTO> createUser(@Validated @RequestBody CreateUserDTO userDTO) {
        try {
            return ResponseEntity.ok(userService.createUser(userDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/private/update")
    private ResponseEntity<UserDTO> updateUser(@CurrentUserEmail String email, @RequestBody UpdateUserDTO userDTO) {
        try {
            return ResponseEntity.ok(userService.updateUser(email, userDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/private/delete")
    private ResponseEntity<Void> deleteUser(@CurrentUserEmail String email) {
        try {
            userService.deleteUser(email);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/private/roles")
    private ResponseEntity<Map<String, Boolean>> checkUserRole(@CurrentUserEmail String email) {
        try {
            boolean hasRole = userService.userHasRole(email);
            return ResponseEntity.ok(Collections.singletonMap("hasRole", !hasRole));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/private/report")
    private ResponseEntity<Void> reportUser(@CurrentUserEmail String email,
            @RequestBody Map<String, String> userEmailReportered) {
        try {
            userService.reportUser(email, userEmailReportered);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/private/report/{reportedEmail}")
    public ResponseEntity<Boolean> isUserReported(
            @CurrentUserEmail String reporterEmail,
            @PathVariable String reportedEmail) {
        try {
            User user = userService.findByEmail(reporterEmail);
            User userReported = userService.findByEmail(reportedEmail);
            boolean exists = userService.isUserAlreadyReported(user, userReported);
            return ResponseEntity.ok(exists);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/createnotification")
    public ResponseEntity<Void> createNotification(@CurrentUserEmail String email) {
        try {
            // Notification noti = notificationService.createNotification("Hola soy una
            // notificacion",
            // TypesNotification.SUCCESS,
            // "Mensaje de prueba", email);
            // notificationService.sendPrivateNotification(noti, email);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
