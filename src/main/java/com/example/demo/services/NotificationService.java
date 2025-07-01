package com.example.demo.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
// import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.User.NotificationDTO;
import com.example.demo.Exceptions.UserNotFoundException;
import com.example.demo.models.Notification;
import com.example.demo.models.NotificationsUsers;
import com.example.demo.models.Notification.TypesNotification;
import com.example.demo.repositories.NotificationsRepository;
import com.example.demo.repositories.NotificationsUsersRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.specifications.NotificationsSpecifications;

import lombok.AllArgsConstructor;

import com.example.demo.models.User;

@Service
@AllArgsConstructor
public class NotificationService {

    // private final SimpMessagingTemplate simpMessagingTemplate;

    private final NotificationsRepository notificationsRepository;

    private final UserRepository userRepository;

    private final NotificationsUsersRepository notificationsUsersRepository;

    public Notification createNotification(String title, TypesNotification type, String message) {
        Notification newNotification = new Notification(title, type, message);
        notificationsRepository.save(newNotification);
        return newNotification;
    }

    public void sendNotifications(List<String> usersEmailsToNotify, Notification notification, String jobId) {

        List<User> users = userRepository.findByEmailIn(usersEmailsToNotify);

        List<NotificationsUsers> notificationsUsers = users.stream()
                .map(user -> new NotificationsUsers(notification, user, jobId)).toList();

        if (!notificationsUsers.isEmpty()) {
            notificationsUsersRepository.saveAll(notificationsUsers);
        }

        List<String> foundEmails = notificationsUsers.stream()
                .map(notificationUser -> notificationUser.getUser().getEmail())
                .toList();

        List<String> notFoundEmails = usersEmailsToNotify.stream().filter(email -> !foundEmails.contains(email))
                .toList();

        if (!notFoundEmails.isEmpty()) {
            throw new UserNotFoundException(notFoundEmails.get(0));
        }

    }

    public Page<NotificationDTO> getNotificationsByUser(String userEmail, Pageable pageable) {
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new UserNotFoundException(userEmail));

        // Pageable sortedByCreatedAt = PageRequest.of(pageable.getPageNumber(),
        // pageable.getPageSize(),
        // Sort.by(Sort.Direction.DESC, "createdAt"));

        Pageable sortedByCreatedAt = PageRequest.of(pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC, "notification.createdAt"));

        Page<NotificationsUsers> notificationsUsersPage = notificationsUsersRepository
                .findAll(NotificationsSpecifications.getNotificationsByUserId(user.getUserId()), sortedByCreatedAt);

        Page<NotificationDTO> notificationDTOPage = notificationsUsersPage
                .map(notification -> new NotificationDTO(notification.getNotification(), notification));

        return notificationDTOPage;
    }

    public void markAsRead(String email, List<String> notificationsIds) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));

        List<NotificationsUsers> notificationsUsers = notificationsUsersRepository.findAll(
                NotificationsSpecifications.getNotificationsByUserIdAndId(user.getUserId(), notificationsIds));

        notificationsUsers.forEach(notificationUser -> notificationUser.setRead_status(true));

        notificationsUsersRepository.saveAll(notificationsUsers);

    }

    public void markAsDeleted(String email, List<String> notifcicationsIds) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));

        List<NotificationsUsers> notificationsUsers = notificationsUsersRepository.findAll(
                NotificationsSpecifications.getNotificationsByUserIdAndIdandDeletedIsFalse(user.getUserId(),
                        notifcicationsIds));

        notificationsUsers.forEach(notificationUser -> notificationUser.setDeleted(true));

        notificationsUsersRepository.saveAll(notificationsUsers);

    }

    // public void sendNotification(Notification notification) {
    // simpMessagingTemplate.convertAndSend("/topic/notifications", notification);
    // }

    // public void sendPrivateNotification(Notification notification, String
    // userEmail) {

    // simpMessagingTemplate.convertAndSendToUser(userEmail,
    // "/specific-user/notifications", notification);
}
