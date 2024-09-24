package com.example.demo.Exceptions;

public class ReportExistsException extends RuntimeException {

    public ReportExistsException(String message) {
        super(message);
    }

}
