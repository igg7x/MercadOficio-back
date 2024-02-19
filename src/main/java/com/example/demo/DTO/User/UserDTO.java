package com.example.demo.DTO.User;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class UserDTO {

    private String name;
    private String surname;
    private String email;
    private String image;
}
