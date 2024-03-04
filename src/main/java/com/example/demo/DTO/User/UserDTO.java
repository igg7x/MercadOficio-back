package com.example.demo.DTO.User;

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
    private String biography;
}
