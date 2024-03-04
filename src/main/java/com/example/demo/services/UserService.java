package com.example.demo.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.example.demo.DTO.User.CreateUserDTO;
import com.example.demo.DTO.User.UserDTO;
import com.example.demo.DTO.User.Offering.CreateUserOfferingDTO;
import com.example.demo.DTO.User.Offering.UserOfferingDTO;
import com.example.demo.DTO.User.Offering.UsersOfferingDTO;
import com.example.demo.models.User;
import com.example.demo.models.UserOffering;
import com.example.demo.repositories.UserOfferingRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.mapper.UserMapper;

import jakarta.transaction.Transactional;

@Service
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final UserOfferingRepository userOfferingRepository;

    // private final ReviewService reviewService;
    public UserService(UserMapper userMapper, UserRepository userRepository,
            UserOfferingRepository userOfferingRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.userOfferingRepository = userOfferingRepository;
        // this.reviewService = reviewService;
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
        Optional<UserOffering> userOffering = userOfferingRepository.findByUserId(user);

        return userMapper.UsertoUserDTO(user);
    }

    @Transactional
    public UserDTO createUser(CreateUserDTO createUserDTO) {
        User user = userMapper.CreateUserDTOtoUser(createUserDTO);
        user = userRepository.save(user);
        return userMapper.UsertoUserDTO(user);
    }

    @Transactional
    public UserOfferingDTO createUserOffering(CreateUserOfferingDTO createUserOfferingDto, String email) {

        User user = userRepository.findByEmailAndDeleteAtIsNull(email).orElse(null);
        if (user == null) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "User not found");
        }
        UserOffering userOfferingCreated = userMapper.CreateUserOfferingDTOtoUserOffering(createUserOfferingDto,
                user);

        userOfferingCreated = userOfferingRepository.save(userOfferingCreated);
        return userMapper.UserOfferingtoUserOfferingDTO(userOfferingCreated, user);
    }

    public List<UsersOfferingDTO> getUsersOffering() {
        List<UserOffering> userOfferingList = userOfferingRepository.findAll();

        return null;
    }

}
