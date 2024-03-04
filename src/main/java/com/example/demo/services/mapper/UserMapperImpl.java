package com.example.demo.services.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.demo.DTO.User.CreateUserDTO;
import com.example.demo.DTO.User.UpdateUserDTO;
import com.example.demo.DTO.User.UserDTO;
import com.example.demo.DTO.User.Offering.CreateUserOfferingDTO;
import com.example.demo.DTO.User.Offering.UserOfferingDTO;
import com.example.demo.DTO.User.Offering.UsersOfferingDTO;
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateUserFromDTO'");
    }

    @Override
    public UserOffering CreateUserOfferingDTOtoUserOffering(CreateUserOfferingDTO createUserOfferingDto, User user) {

        UserOffering userOffering = new UserOffering();
        userOffering.setUser(user);
        userOffering.setLocation(createUserOfferingDto.getLocation());
        userOffering.setPrice(createUserOfferingDto.getPrice());
        userOffering.setExperience(createUserOfferingDto.getExperience());
        userOffering.setWorkDayStart(createUserOfferingDto.getWorkDayStart());
        userOffering.setWorkDayEnd(createUserOfferingDto.getWorkDayEnd());
        userOffering.setReviews(new ArrayList<>());
        userOffering.setUserCategories(createUserOfferingDto.getUserCategories());
        return userOffering;
    }

    @Override
    public UserOfferingDTO UserOfferingtoUserOfferingDTO(UserOffering userOfferingCreated, User user) {

        UserOfferingDTO userOfferingDTO = new UserOfferingDTO();
        userOfferingDTO.setName(user.getName());
        userOfferingDTO.setSurname(user.getSurname());
        userOfferingDTO.setEmail(user.getEmail());
        userOfferingDTO.setBiography(user.getBiography());
        userOfferingDTO.setLocation(userOfferingCreated.getLocation());
        userOfferingDTO.setPrice(userOfferingCreated.getPrice());
        userOfferingDTO.setWorkDayStart(userOfferingCreated.getWorkDayStart());
        userOfferingDTO.setWorkDayEnd(userOfferingCreated.getWorkDayEnd());
        userOfferingDTO.setReviews(userOfferingDTO.getReviews());
        userOfferingDTO.setCategories(userOfferingDTO.getCategories());

        return userOfferingDTO;
    }

    @Override
    public List<UsersOfferingDTO> UserOfferingListtoUserOfferingDTOList(List<UserOffering> userOfferingList) {

        List<UsersOfferingDTO> usersOfferingDTOList = new ArrayList<>();
        for (UserOffering userOffering : userOfferingList) {
            UsersOfferingDTO usersOfferingDTO = new UsersOfferingDTO();
            usersOfferingDTO.setName(userOffering.getUser().getName());
            usersOfferingDTO.setSurname(userOffering.getUser().getSurname());
            usersOfferingDTO.setEmail(userOffering.getUser().getEmail());
            usersOfferingDTO.setBiography(userOffering.getUser().getBiography());
            usersOfferingDTO.setLocation(userOffering.getLocation());
            usersOfferingDTO.setPrice(userOffering.getPrice());
            usersOfferingDTO.setWorkDayStart(userOffering.getWorkDayStart());
            usersOfferingDTO.setWorkDayEnd(userOffering.getWorkDayEnd());
            usersOfferingDTOList.add(usersOfferingDTO);
        }
        return usersOfferingDTOList;
    }

}
