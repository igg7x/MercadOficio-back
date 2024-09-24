package com.example.demo.services.mapper.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.example.demo.DTO.Categories.CategorieDTO;
import com.example.demo.DTO.User.CreateUserDTO;
import com.example.demo.DTO.User.UpdateUserDTO;
import com.example.demo.DTO.User.UserDTO;
import com.example.demo.DTO.User.Customer.UserCustomerDTO;
import com.example.demo.DTO.User.Offering.CreateUserOfferingDTO;
import com.example.demo.DTO.User.Offering.UpdateUserOfferingDTO;
import com.example.demo.DTO.User.Offering.UserOfferingDTO;
import com.example.demo.DTO.User.Offering.UsersOfferingDTO;
import com.example.demo.auth.Roles;
import com.example.demo.models.Category;
import com.example.demo.models.User;
import com.example.demo.models.UserCustomer;
import com.example.demo.models.UserOffering;

@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDTO UsertoUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setName(user.getName());
        userDTO.setSurname(user.getSurname());
        userDTO.setEmail(user.getEmail());
        userDTO.setPicture(user.getPicture());
        userDTO.setBiography(user.getBiography());
        userDTO.setLocation(user.getLocation());
        userDTO.setPhone(user.getPhone());
        userDTO.setRoles(user.getRoles());
        return userDTO;
    }

    @Override
    public User CreateUserDTOtoUser(CreateUserDTO createUserDTO) {

        Set<Roles> userRoles = createUserDTO.getRoles()
                .stream()
                .map(Roles::valueOf)
                .collect(Collectors.toSet());

        User user = new User();
        user.setName(createUserDTO.getName());
        user.setSurname(createUserDTO.getSurname());
        user.setEmail(createUserDTO.getEmail());
        user.setPicture(createUserDTO.getPicture());
        user.setLocation(createUserDTO.getLocation());
        user.setBiography(createUserDTO.getBiography());
        user.setPhone(createUserDTO.getPhone());
        user.setRoles(userRoles);
        return user;
    }

    @Override
    public User updateUserFromDTO(UpdateUserDTO updateUserDTO, User user) {

        if (updateUserDTO.getBiography() != null) {
            user.setBiography(updateUserDTO.getBiography());
        }
        if (updateUserDTO.getLocation() != null) {
            user.setLocation(updateUserDTO.getLocation());
        }
        if (updateUserDTO.getPhone() != null) {
            user.setPhone(updateUserDTO.getPhone());
        }
        return user;
    }

    @Override
    public UserOffering CreateUserOfferingDTOtoUserOffering(CreateUserOfferingDTO createUserOfferingDto, User user,
            List<Category> categories) {

        UserOffering userOffering = new UserOffering();
        userOffering.setUser(user);
        userOffering.setUserCategories(categories);
        return userOffering;
    }

    @Override
    public UserOfferingDTO UserOfferingtoUserOfferingDTO(User user,
            List<CategorieDTO> categories) {

        UserOfferingDTO userOfferingDTO = new UserOfferingDTO();
        userOfferingDTO.setName(user.getName());
        userOfferingDTO.setSurname(user.getSurname());
        userOfferingDTO.setEmail(user.getEmail());
        userOfferingDTO.setBiography(user.getBiography());
        userOfferingDTO.setLocation(user.getLocation());
        userOfferingDTO.setPicture(user.getPicture());
        userOfferingDTO.setCategories(categories);
        userOfferingDTO.setPhone(user.getPhone());
        userOfferingDTO.setRoles(user.getRoles());
        return userOfferingDTO;
    }

    @Override
    public List<UsersOfferingDTO> UserOfferingListtoUserOfferingDTOList(List<UserOffering> userOfferingList) {

        List<UsersOfferingDTO> usersOfferingDTO = new ArrayList<>();
        for (UserOffering userOffering : userOfferingList) {

            UsersOfferingDTO usersOffering = new UsersOfferingDTO();
            usersOffering.setName(userOffering.getUser().getName());
            usersOffering.setSurname(userOffering.getUser().getSurname());
            usersOffering.setEmail(userOffering.getUser().getEmail());
            usersOffering.setLocation(userOffering.getUser().getLocation());
            usersOffering.setPicture(userOffering.getUser().getPicture());
            userOffering.setCalification(userOffering.getCalification());
            usersOffering.setPhone(userOffering.getUser().getPhone());
            usersOffering.setCategories(userOffering.getUserCategories().stream().map(category -> {
                CategorieDTO categorieDTO = new CategorieDTO();
                categorieDTO.setName(category.getCategoryName());
                return categorieDTO;
            }).collect(Collectors.toList()));
            // usersOffering.setPrice(userOffering.getPrice());
            // usersOffering.setWorkDayStart(userOffering.getWorkDayStart());
            // usersOffering.setWorkDayEnd(userOffering.getWorkDayEnd());

            usersOfferingDTO.add(usersOffering);
        }
        return usersOfferingDTO;
    }

    @Override
    public UserOffering updateUserOfferingFromDTO(UpdateUserOfferingDTO userOfferingDTO, UserOffering userOffering) {

        // if (userOfferingDTO.getPrice() != null) {
        // userOffering.setPrice(userOfferingDTO.getPrice());
        // }
        // if (userOfferingDTO.getWorkDayStart() != null) {
        // userOffering.setWorkDayStart(userOfferingDTO.getWorkDayStart());
        // }
        // if (userOfferingDTO.getWorkDayEnd() != null) {
        // userOffering.setWorkDayEnd(userOfferingDTO.getWorkDayEnd());
        // }

        return userOffering;
    }

    @Override
    public UsersOfferingDTO UserOfferingtoUserOfferingDTO(UserOffering userOffering) {
        UsersOfferingDTO usersOffering = new UsersOfferingDTO();
        usersOffering.setName(userOffering.getUser().getName());
        usersOffering.setSurname(userOffering.getUser().getSurname());
        usersOffering.setEmail(userOffering.getUser().getEmail());
        usersOffering.setLocation(userOffering.getUser().getLocation());
        usersOffering.setBiography(userOffering.getUser().getBiography());
        usersOffering.setPicture(userOffering.getUser().getPicture());
        userOffering.setCalification(userOffering.getCalification());
        usersOffering.setRoles(userOffering.getUser().getRoles());
        usersOffering.setPhone(userOffering.getUser().getPhone());
        usersOffering.setCategories(userOffering.getUserCategories().stream().map(category -> {
            CategorieDTO categorieDTO = new CategorieDTO();
            categorieDTO.setName(category.getCategoryName());
            return categorieDTO;
        }).collect(Collectors.toList()));
        return usersOffering;
    }

    @Override
    public UserCustomerDTO UserCustomertoUserCustomerDTO(UserCustomer userCustomer) {
        UserCustomerDTO userCustomerDTO = new UserCustomerDTO();
        userCustomerDTO.setName(userCustomer.getUser().getName());
        userCustomerDTO.setSurname(userCustomer.getUser().getSurname());
        userCustomerDTO.setLocation(userCustomer.getUser().getLocation());
        userCustomerDTO.setEmail(userCustomer.getUser().getEmail());
        userCustomerDTO.setBiography(userCustomer.getUser().getBiography());
        userCustomerDTO.setPicture(userCustomer.getUser().getPicture());
        userCustomerDTO.setPhone(userCustomer.getUser().getPhone());
        userCustomerDTO.setRoles(userCustomer.getUser().getRoles());
        return userCustomerDTO;
    }

}
