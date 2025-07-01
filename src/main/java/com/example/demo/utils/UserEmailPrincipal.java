package com.example.demo.utils;

import java.security.Principal;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserEmailPrincipal implements Principal {

    private String email;

    @Override
    public String getName() {
        return this.email;
    }

}
