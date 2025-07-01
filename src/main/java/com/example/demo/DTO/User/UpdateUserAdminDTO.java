package com.example.demo.DTO.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateUserAdminDTO {

    @NonNull
    private String email;
    @NonNull
    private Boolean isBanned;

}
