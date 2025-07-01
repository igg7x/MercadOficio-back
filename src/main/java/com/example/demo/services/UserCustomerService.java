package com.example.demo.services;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.example.demo.DTO.User.UpdateUserDTO;
import com.example.demo.DTO.User.UserDTO;
import com.example.demo.auth.Roles;
import com.example.demo.models.User;
import com.example.demo.models.UserCustomer;
import com.example.demo.repositories.UserCustomerRepository;
import com.example.demo.services.mapper.User.UserMapper;

import jakarta.transaction.Transactional;


@Service
public class UserCustomerService {

    private final UserCustomerRepository userCustomerRepository;
    private final UserService userService;
    private final UserMapper userMapper;

    public UserCustomerService(UserCustomerRepository userCustomerRepository, UserService userService,
            UserMapper userMapper) {
        this.userCustomerRepository = userCustomerRepository;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @Transactional
    public UserDTO createUserCustomer(String userEmail) {
        User user = userService.findByEmail(userEmail);
        if (user == null) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "User not found");
        }

        UserCustomer userCustomer = new UserCustomer();
        userCustomer.setUser(user);
        userCustomerRepository.save(userCustomer);
        UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        Set<Roles> roles = user.getRoles();
        roles.add(Roles.USER_CUSTOMER);
        updateUserDTO.setNewRoles(roles);
        userService.updateUser(userEmail, updateUserDTO);
        return userMapper.UsertoUserDTO(user);
    }

    public UserCustomer getUserCustomer(String userEmail) {
        User user = userService.findByEmail(userEmail);

        if (user == null) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "User not found");
        }

        UserCustomer userCustomer = userCustomerRepository.findByUser(user).orElse(null);
        if (userCustomer == null) {
            return null;
            // throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "User Offering not
            // found");
        }
        return userCustomer;
    }

    public long getTotalUsersCustomers() {
        return userCustomerRepository.count();
    }   

}
