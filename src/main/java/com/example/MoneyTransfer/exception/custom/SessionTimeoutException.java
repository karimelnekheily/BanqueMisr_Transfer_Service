package com.example.MoneyTransfer.exception.custom;

public class SessionTimeoutException extends RuntimeException {
    public SessionTimeoutException(String message) {
        super(message);
    }
}
