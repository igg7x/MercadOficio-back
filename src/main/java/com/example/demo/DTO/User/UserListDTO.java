package com.example.demo.DTO.User;

import java.util.Set;

import com.example.demo.auth.Roles;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserListDTO {
    private String name;
    private String email;
    private Boolean isBanned;
    private Set<Roles> roles;
}
