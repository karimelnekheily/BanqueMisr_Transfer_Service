package com.example.MoneyTransfer.exception.custom;

public class PasswordNotMatchException extends RuntimeException {
    public PasswordNotMatchException(String message ) {

        super(message);
    }
}
