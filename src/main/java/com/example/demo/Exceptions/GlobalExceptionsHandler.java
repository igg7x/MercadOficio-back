package com.example.demo.Exceptions;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionsHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorObject> handleUserNotFoundException(UserNotFoundException e, WebRequest request) {

        ErrorObject error = new ErrorObject();
        error.setStatusCode(HttpStatus.NOT_FOUND.value());
        error.setMessage(e.getMessage());
        error.setTimestamp(new Date());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler()
    public ResponseEntity<ErrorObject> handleReportAlreadyExitsException(Exception e, WebRequest request) {

        ErrorObject error = new ErrorObject();
        error.setStatusCode(HttpStatus.CONFLICT.value());
        error.setMessage(e.getMessage());
        error.setTimestamp(new Date());

        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

}
