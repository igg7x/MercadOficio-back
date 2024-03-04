package com.example.demo.DTO.User;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@NoArgsConstructor
public class CreateUserDTO {

    private String name;
    private String surname;
    private String email;
    private String password;
    private String biography;
}
