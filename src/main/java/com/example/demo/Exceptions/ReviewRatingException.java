package com.example.demo.Exceptions;

public class ReviewRatingException extends RuntimeException {
    public ReviewRatingException(String message) {
        super(message);
    }

    public ReviewRatingException(String message, Throwable cause) {
        super(message, cause);
    }
}
