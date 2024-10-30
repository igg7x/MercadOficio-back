package com.example.demo.Exceptions;

import java.util.List;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotFoundException(List<String> notFoundEmails) {
        super("Users with emails: " + String.join(",", notFoundEmails) + " not found");
    }
}
