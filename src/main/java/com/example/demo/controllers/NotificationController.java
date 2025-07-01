package com.example.demo.controllers;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.messaging.handler.annotation.MessageMapping;
// import org.springframework.messaging.handler.annotation.SendTo;
// import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.User.NotificationDTO;
import com.example.demo.Exceptions.UserNotFoundException;
import com.example.demo.auth.CurrentUserEmail;

import com.example.demo.services.NotificationService;

@RestController
@RequestMapping("/api/v1/notifications")
@CrossOrigin(origins = "*")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    // private final SimpMessagingTemplate simpMessagingTemplate;

    // @MessageMapping("/send")
    // @SendTo("/topic/notifications")
    // public Notification sendNotification(Notification notification) {
    // return notification;
    // }

    // @MessageMapping("/private")
    // public void sendPrivateNotification(Notification notification, Principal
    // principal) {
    // simpMessagingTemplate.convertAndSendToUser(principal.getName(),
    // "/specific-user",
    // notification);
    // }

    @GetMapping("/get")
    public ResponseEntity<Page<NotificationDTO>> getNotifications(@CurrentUserEmail String email, Pageable pageable) {
        try {
            return ResponseEntity.ok(notificationService.getNotificationsByUser(email, pageable));
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().build();
        }

    }

    @PatchMapping("/mark-as-read")
    public ResponseEntity<?> markAsRead(@CurrentUserEmail String email, @RequestBody List<String> notificationsIds) {
        try {
            notificationService.markAsRead(email, notificationsIds);
            return ResponseEntity.ok("Notificationes Actualizadas");
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/mark-as-deleted")
    public ResponseEntity<?> markAsDeleted(@CurrentUserEmail String email, @RequestBody List<String> notificationsIds) {
        try {
            notificationService.markAsDeleted(email, notificationsIds);
            return ResponseEntity.ok("Notificationes Actualizadas");
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
