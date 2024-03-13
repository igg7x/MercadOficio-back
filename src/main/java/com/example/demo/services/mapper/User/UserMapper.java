package com.example.demo.services.mapper.User;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.example.demo.DTO.Categories.CategorieDTO;
import com.example.demo.DTO.User.CreateUserDTO;
import com.example.demo.DTO.User.UpdateUserDTO;
import com.example.demo.DTO.User.UserDTO;
import com.example.demo.DTO.User.Offering.CreateUserOfferingDTO;
import com.example.demo.DTO.User.Offering.UpdateUserOfferingDTO;
import com.example.demo.DTO.User.Offering.UserOfferingDTO;
import com.example.demo.DTO.User.Offering.UsersOfferingDTO;
import com.example.demo.models.User;
import com.example.demo.models.UserOffering;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO UsertoUserDTO(User user);

    User UserDTOtoUser(UserDTO userDTO);

    CreateUserDTO UsertoCreateUserDTO(User user);

    User CreateUserDTOtoUser(CreateUserDTO createUserDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
    User updateUserFromDTO(UpdateUserDTO updateUserDTO, @MappingTarget User user);

    UserOffering CreateUserOfferingDTOtoUserOffering(CreateUserOfferingDTO createUserOfferingDto, User user);

    UserOfferingDTO UserOfferingtoUserOfferingDTO(UserOffering userOfferingCreated, User user,
            List<CategorieDTO> categories);

    List<UsersOfferingDTO> UserOfferingListtoUserOfferingDTOList(List<UserOffering> userOfferingList);

    UserOffering updateUserOfferingFromDTO(UpdateUserOfferingDTO userOfferingDTO, UserOffering userOffering);
}
