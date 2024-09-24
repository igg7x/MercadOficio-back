package com.example.demo.services;

import java.util.Date;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.example.demo.DTO.User.CreateUserDTO;
import com.example.demo.DTO.User.UpdateUserDTO;
import com.example.demo.DTO.User.UserDTO;
import com.example.demo.Exceptions.ReportExistsException;
import com.example.demo.models.Report;
import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.mapper.User.UserMapper;

import jakarta.transaction.Transactional;

@Service
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final ReportService reportService;

    public UserService(UserMapper userMapper, UserRepository userRepository, ReportService reportService) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.reportService = reportService;
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "User not found");
        }
        return userMapper.UsertoUserDTO(user);
    }

    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmailAndDeleteAtIsNullAndIsBannedIsFalse(email).orElse(null);
        if (user == null) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "User not found");
        }
        return userMapper.UsertoUserDTO(user);
    }

    public User findByEmail(String email) {
        User user = userRepository.findByEmailAndDeleteAtIsNullAndIsBannedIsFalse(email).orElse(null);
        if (user == null)
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "User not found");
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
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "User not found");
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
        userRepository.save(user);
    }

    public Boolean userHasRole(String userEmail) {
        User user = findByEmail(userEmail);
        return user.getRoles().isEmpty();
    }

    public void reportUser(String email, Map<String, String> userEmailReportered) {
        User reporter = findByEmail(email);
        User reported = findByEmail(userEmailReportered.get("userEmailReportered"));
        if (isUserAlreadyReported(reporter, reported)) {
            throw new ReportExistsException("Report already exists");
        }
        Report report = createNewReport(reporter, reported);
        incrementStrikesCount(reported);
        saveReportAndUser(report, reported);
    }

    public Report getReport(String email, Map<String, String> userEmailReportered) {
        User user = findByEmail(email);
        User userReported = findByEmail(userEmailReportered.get("userEmailReportered"));
        return reportService.getReportIfExist(user.getUserId(), userReported.getUserId());

    }

    public boolean isUserAlreadyReported(User user, User userReported) {
        return reportService.getReportIfExist(user.getUserId(), userReported.getUserId()) != null;
    }

    private Report createNewReport(User user, User userReported) {
        return new Report(user, userReported);
    }

    private void incrementStrikesCount(User userReported) {
        userReported.setStrikesCount(userReported.getStrikesCount() + 1);
    }

    private void saveReportAndUser(
            Report report, User user) {
        reportService.createReport(report);
        userRepository.save(user);
    }
}
