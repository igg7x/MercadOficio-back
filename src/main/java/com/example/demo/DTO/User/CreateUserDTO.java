package com.example.demo.DTO.User;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class CreateUserDTO {

    private String name;

    private String surname;

    private String email;

    private String password;

    private String image;

    private String biography;

}
