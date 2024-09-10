package com.example.MoneyTransfer.exception.custom;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class SessionExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(SessionTimeoutException.class)
    public ResponseEntity<String> handleSessionTimeoutException(SessionTimeoutException ex) {
        return new ResponseEntity<>("Session has expired. Please log in again.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
