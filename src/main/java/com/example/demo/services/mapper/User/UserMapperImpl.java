package com.example.demo.services.mapper.User;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.demo.DTO.Categories.CategorieDTO;
import com.example.demo.DTO.Review.ReviewDTO;
import com.example.demo.DTO.User.CreateUserDTO;
import com.example.demo.DTO.User.UpdateUserDTO;
import com.example.demo.DTO.User.UserDTO;
import com.example.demo.DTO.User.Offering.CreateUserOfferingDTO;
import com.example.demo.DTO.User.Offering.UpdateUserOfferingDTO;
import com.example.demo.DTO.User.Offering.UserOfferingDTO;
import com.example.demo.DTO.User.Offering.UsersOfferingDTO;
import com.example.demo.models.Category;
import com.example.demo.models.User;
import com.example.demo.models.UserOffering;

@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDTO UsertoUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setName(user.getName());
        userDTO.setSurname(user.getSurname());
        userDTO.setEmail(user.getEmail());
        userDTO.setBiography(user.getBiography());
        return userDTO;
    }

    @Override
    public User UserDTOtoUser(UserDTO userDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'UserDTOtoUser'");

    }

    @Override
    public CreateUserDTO UsertoCreateUserDTO(User user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'UsertoCreateUserDTO'");
    }

    @Override
    public User CreateUserDTOtoUser(CreateUserDTO createUserDTO) {

        User user = new User();
        user.setName(createUserDTO.getName());
        user.setSurname(createUserDTO.getSurname());
        user.setEmail(createUserDTO.getEmail());
        user.setPassword(createUserDTO.getPassword());
        user.setBiography(createUserDTO.getBiography());

        return user;
    }

    @Override
    public User updateUserFromDTO(UpdateUserDTO updateUserDTO, User user) {

        if (updateUserDTO.getName() != null) {
            user.setName(updateUserDTO.getName());
        }
        if (updateUserDTO.getSurname() != null) {
            user.setSurname(updateUserDTO.getSurname());
        }
        if (updateUserDTO.getBiography() != null) {
            user.setBiography(updateUserDTO.getBiography());
        }
        return user;
    }

    @Override
    public UserOffering CreateUserOfferingDTOtoUserOffering(CreateUserOfferingDTO createUserOfferingDto, User user,
            List<Category> categories) {

        UserOffering userOffering = new UserOffering();
        userOffering.setUser(user);
        userOffering.setLocation(createUserOfferingDto.getLocation());
        userOffering.setPrice(createUserOfferingDto.getPrice());
        userOffering.setExperience(createUserOfferingDto.getExperience());
        userOffering.setWorkDayStart(createUserOfferingDto.getWorkDayStart());
        userOffering.setWorkDayEnd(createUserOfferingDto.getWorkDayEnd());
        userOffering.setReviews(new ArrayList<>());
        userOffering.setUserCategories(categories);
        return userOffering;
    }

    @Override
    public UserOfferingDTO UserOfferingtoUserOfferingDTO(UserOffering userOfferingCreated, User user,
            List<CategorieDTO> categories, List<ReviewDTO> reviews) {

        UserOfferingDTO userOfferingDTO = new UserOfferingDTO();
        userOfferingDTO.setName(user.getName());
        userOfferingDTO.setSurname(user.getSurname());
        userOfferingDTO.setEmail(user.getEmail());
        userOfferingDTO.setBiography(user.getBiography());
        userOfferingDTO.setLocation(userOfferingCreated.getLocation());
        userOfferingDTO.setExperience(userOfferingCreated.getExperience());
        userOfferingDTO.setPrice(userOfferingCreated.getPrice());
        userOfferingDTO.setWorkDayStart(userOfferingCreated.getWorkDayStart());
        userOfferingDTO.setWorkDayEnd(userOfferingCreated.getWorkDayEnd());
        userOfferingDTO.setReviews(reviews);
        userOfferingDTO.setCategories(categories);

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
            usersOffering.setBiography(userOffering.getUser().getBiography());
            usersOffering.setLocation(userOffering.getLocation());
            usersOffering.setExperience(userOffering.getExperience());
            usersOffering.setPrice(userOffering.getPrice());
            userOffering.setCalification(userOffering.getCalification());
            usersOffering.setWorkDayStart(userOffering.getWorkDayStart());
            usersOffering.setWorkDayEnd(userOffering.getWorkDayEnd());
            usersOfferingDTO.add(usersOffering);
        }
        return usersOfferingDTO;
    }

    @Override
    public UserOffering updateUserOfferingFromDTO(UpdateUserOfferingDTO userOfferingDTO, UserOffering userOffering) {

        if (userOfferingDTO.getLocation() != null) {
            userOffering.setLocation(userOfferingDTO.getLocation());
        }
        if (userOfferingDTO.getPrice() != null) {
            userOffering.setPrice(userOfferingDTO.getPrice());
        }
        if (userOfferingDTO.getExperience() != null) {
            userOffering.setExperience(userOfferingDTO.getExperience());
        }
        if (userOfferingDTO.getWorkDayStart() != null) {
            userOffering.setWorkDayStart(userOfferingDTO.getWorkDayStart());
        }
        if (userOfferingDTO.getWorkDayEnd() != null) {
            userOffering.setWorkDayEnd(userOfferingDTO.getWorkDayEnd());
        }

        return userOffering;
    }

}
