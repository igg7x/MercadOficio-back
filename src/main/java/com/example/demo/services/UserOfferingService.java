package com.example.demo.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.example.demo.DTO.User.Offering.CreateUserOfferingDTO;
import com.example.demo.DTO.User.Offering.UpdateUserOfferingDTO;
import com.example.demo.DTO.User.Offering.UserOfferingDTO;
import com.example.demo.DTO.User.Offering.UsersOfferingDTO;
import com.example.demo.models.User;
import com.example.demo.models.UserOffering;
import com.example.demo.repositories.UserOfferingRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.mapper.User.UserMapper;

import jakarta.transaction.Transactional;

@Service
public class UserOfferingService {

    private final UserOfferingRepository userOfferingRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserOfferingService(UserOfferingRepository userOfferingRepository, UserRepository userRepository,
            UserMapper userMapper) {
        this.userOfferingRepository = userOfferingRepository;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserOfferingDTO getUserOfferingByEmail(String email) {
        User user = userRepository.findByEmailAndDeleteAtIsNull(email).orElse(null);
        if (user == null) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "User not found");
        }
        UserOffering userOffering = userOfferingRepository.findByUser(user).orElse(null);
        if (userOffering == null) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "User Offering not found");
        }
        return userMapper.UserOfferingtoUserOfferingDTO(userOffering, user);
    }

    public List<UsersOfferingDTO> getUsersOffering() {
        List<UserOffering> userOfferingList = userOfferingRepository.findAll();
        return userMapper.UserOfferingListtoUserOfferingDTOList(userOfferingList);
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

    @Transactional
    public UserOfferingDTO updateUserOffering(String email, UpdateUserOfferingDTO userOfferingDTO) {
        User user = userRepository.findByEmailAndDeleteAtIsNull(email).orElse(null);
        if (user == null) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "User not found");
        }
        UserOffering userOffering = userOfferingRepository.findByUser(user).orElse(null);

        if (userOffering == null) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "User Offering not found");
        }

        userOffering = userMapper.updateUserOfferingFromDTO(userOfferingDTO, userOffering);
        userOffering = userOfferingRepository.save(userOffering);
        return userMapper.UserOfferingtoUserOfferingDTO(userOffering, user);
    }

}