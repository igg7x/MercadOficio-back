package com.example.demo.DTO.User;

import java.util.Set;

import com.example.demo.auth.Roles;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Data
public class UserDTO {

    private String name;
    private String surname;
    private String email;
    private Long phone;
    private String picture;
    private String biography;
    private String location;
    private Set<Roles> roles;
}
