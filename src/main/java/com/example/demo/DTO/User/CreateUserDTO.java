package com.example.demo.DTO.User;

// import java.util.List;

import org.springframework.lang.Nullable;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Data
@NoArgsConstructor
public class CreateUserDTO {

    private String name;
    private String surname;
    private String email;
    private String picture;
    // private List<String> roles;
    @Nullable
    private Long phone;
    @Nullable
    private String location;
    @Nullable
    private String password;
    @Nullable
    private String biography;
}
