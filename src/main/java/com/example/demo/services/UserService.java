package com.example.demo.services;

import java.util.Date;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.example.demo.DTO.User.CreateUserDTO;
import com.example.demo.DTO.User.UpdateUserDTO;
import com.example.demo.DTO.User.UserDTO;
import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.mapper.User.UserMapper;

import jakarta.transaction.Transactional;

@Service
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public UserService(UserMapper userMapper, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "User not found");
        }
        return userMapper.UsertoUserDTO(user);
    }

    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmailAndDeleteAtIsNull(email).orElse(null);
        if (user == null) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "User not found");
        }
        return userMapper.UsertoUserDTO(user);
    }

    public User findByEmail(String email) {
        User user = userRepository.findByEmailAndDeleteAtIsNull(email).orElse(null);
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
        User user = userRepository.findByEmailAndDeleteAtIsNull(email).orElse(null);
        if (user == null) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "User not found");
        }
        user = userMapper.updateUserFromDTO(updateUserDTO, user);
        user = userRepository.save(user);
        return userMapper.UsertoUserDTO(user);
    }

    @Transactional
    public void deleteUser(String email) {

        User user = userRepository.findByEmailAndDeleteAtIsNull(email).orElse(null);
        if (user == null) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "User not found");
        }
        user.setDeleteAt(Date.from(java.time.Instant.now()));
        userRepository.save(user);
    }

    // public void getUsersByCriteria() {
    // Specification<User> spec = Specification
    // .where((root, query, criteriaBuilder) -> root.get("userId").isNotNull());

    // List<User> users = userRepository.findAll(spec);
    // for (User user : users) {
    // System.out.println(user.getEmail());
    // }
    // }

}
