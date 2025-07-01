package com.example.demo.services;

import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.example.demo.DTO.User.CreateUserDTO;
import com.example.demo.DTO.User.UpdateUserAdminDTO;
import com.example.demo.DTO.User.UpdateUserDTO;
import com.example.demo.DTO.User.UserDTO;
import com.example.demo.DTO.User.UserListDTO;
import com.example.demo.Exceptions.ReportExistsException;
import com.example.demo.Exceptions.UserNotFoundException;
import com.example.demo.auth.Roles;
import com.example.demo.models.Notification;
import com.example.demo.models.Report;
import com.example.demo.models.Report.ReportReasonEnum;
import com.example.demo.models.User;
import com.example.demo.models.Notification.TypesNotification;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.mapper.User.UserMapper;
import com.example.demo.services.specifications.UserSpecifications;

import jakarta.transaction.Transactional;

@Service
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final ReportService reportService;
    // private final EmailService emailService;
    private final NotificationService notificationService;

    public UserService(UserMapper userMapper, UserRepository userRepository, ReportService reportService,
            EmailService emailService, NotificationService notificationService) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.reportService = reportService;
        // this.emailService = emailService;
        this.notificationService = notificationService;
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        return userMapper.UsertoUserDTO(user);
    }

    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            throw new UserNotFoundException("No se encontro al usuario con el siguiente email : " + email);
        }
        return userMapper.UsertoUserDTO(user);
    }

    public User findByEmail(String email) {
        User user = userRepository.findByEmailAndDeleteAtIsNull(email).orElse(null);
        if (user == null)
            throw new UserNotFoundException("No se encontro al usuario con el siguiente email : " + email);
        return user;
    }

    public User findByEmailIncludingDeleted(String email) {
        User user = userRepository.findByEmailAndDeleteAtIsNull(email).orElse(null);
        if (user == null)
            throw new UserNotFoundException("No se encontro al usuario con el siguiente email : " + email);
        return user;
    }

    @Transactional
    public UserDTO createUser(CreateUserDTO createUserDTO) {
        User user = userMapper.CreateUserDTOtoUser(createUserDTO);
        user = userRepository.save(user);
        return userMapper.UsertoUserDTO(user);
    }

    @Transactional
    public UserDTO updateUser(String email, UpdateUserDTO updateUserDTO) {
        User user = userRepository.findByEmailAndDeleteAtIsNullAndIsBannedIsFalse(email).orElse(null);
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        user = userMapper.updateUserFromDTO(updateUserDTO, user);
        user = userRepository.save(user);
        return userMapper.UsertoUserDTO(user);
    }

    @Transactional
    public void deleteUser(String email) {

        User user = userRepository.findByEmailAndDeleteAtIsNullAndIsBannedIsFalse(email).orElse(null);
        if (user == null) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "User not found");
        }
        user.setDeleteAt(Date.from(java.time.Instant.now()));
        String randomSequence = UUID.randomUUID().toString().substring(0, 8);
        user.setEmail("anonimo_" + randomSequence + "@email.com");
        user.setBiography("");
        user.setName("");
        user.setSurname("");
        user.setPhone(0L);
        user.setLocation("");
        user.setPicture("");
        user.setRoles(null);
        userRepository.save(user);
    }

    public Boolean userHasRole(String userEmail) {
        User user = findByEmail(userEmail);
        return user.getRoles().isEmpty();
    }

    public void reportUser(String email, String userEmailReportered, String reportReason) {
        User reporter = findByEmail(email);
        User reported = findByEmail(userEmailReportered);
        if (isUserAlreadyReported(reporter, reported)) {
            throw new ReportExistsException("Report already exists");
        }
        ReportReasonEnum reportReasonEnum = null;
        try {
            reportReasonEnum = ReportReasonEnum.valueOf(reportReason);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid report reason: " + reportReason, e);
        }

        Report report = createNewReport(reporter, reported, reportReasonEnum);
        incrementStrikesCount(reported);
        saveReportAndUser(report, reported);
        notifyUserAboutReport(userEmailReportered);
    }

    private void notifyUserAboutReport(String userEmail) {

        Notification notificationReport = notificationService.createNotification("Reporte", TypesNotification.WARNING,
                "Te han reportado. Recuerda seguir las normas de la plataforma. Si alcanzas " +
                        "3 reportes, tu cuenta será inhabilitada.");

        notificationService.sendNotifications(Collections.singletonList(userEmail),
                notificationReport, null);

    }

    public Report getReport(String email, Map<String, String> userEmailReportered) {
        User user = findByEmail(email);
        User userReported = findByEmail(userEmailReportered.get("userEmailReportered"));
        return reportService.getReportIfExist(user.getUserId(), userReported.getUserId());
    }

    public boolean isUserAlreadyReported(User user, User userReported) {
        return reportService.getReportIfExist(user.getUserId(), userReported.getUserId()) != null;
    }

    private Report createNewReport(User user, User userReported, ReportReasonEnum reportReason) {
        return new Report(user, userReported, true, reportReason);
    }

    private void incrementStrikesCount(User userReported) {
        userReported.setStrikesCount(userReported.getStrikesCount() + 1);
    }

    private void saveReportAndUser(
            Report report, User user) {
        reportService.createReport(report);
        userRepository.save(user);
    }

    public Page<UserListDTO> getUsers(String email, Pageable pageable) {
        // User user = findByEmail(email);
        // if (!user.getRoles().contains(Roles.ADMIN)) {
        // throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "User not
        // authorized");
        // }
        Page<User> users = userRepository.findAll(UserSpecifications.getUsers(), pageable);
        return users.map(userMapper::UsertoUserListDTO);
    }

    @Transactional
    public UserDTO updateUserByAdmin(String emailAdmin, UpdateUserAdminDTO userDTO) {
        // User user = findByEmail(emailAdmin);
        User userToUpdate = findByEmailIncludingDeleted(userDTO.getEmail());
        if (!userDTO.getIsBanned()) {
            userToUpdate.setStrikesCount(0);
            reportService.desactivateReports(userToUpdate);
        }
        userToUpdate.setIsBanned(userDTO.getIsBanned());
        userToUpdate = userRepository.save(userToUpdate);
        return userMapper.UsertoUserDTO(userToUpdate);
    }

    public long getTotalUsers() {
        return userRepository.countBydeleteAtIsNull();
    }

    public Double getAverageActiveUsers() {
        long totalUsers = getTotalUsers();
        long totalActiveUsers = getTotalActiveUsers();
        if (totalUsers <= 0) {
            return 0.0;
        }
        return ((double) totalActiveUsers / totalUsers) * 100;
    }

    public long getTotalActiveUsers() {
        return userRepository.countByIsBannedIsFalseAndDeleteAtIsNull();
    }

    public boolean isAdmin(String email) {
        User user = findByEmail(email);
        return user.getRoles().contains(Roles.ADMIN);
    }

}
