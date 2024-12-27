package com.example.demo.Exceptions;

public class ReviewExistsException extends RuntimeException {
    public ReviewExistsException(String message) {
        super(message);
    }

    public ReviewExistsException(String message, Throwable cause) {
        super(message, cause);
    }

}
