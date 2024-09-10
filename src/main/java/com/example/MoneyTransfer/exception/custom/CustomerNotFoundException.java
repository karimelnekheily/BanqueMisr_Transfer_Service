package com.example.MoneyTransfer.exception.custom;

public class CustomerNotFoundException extends Exception {

    // Default constructor
    public CustomerNotFoundException() {
        super();
    }

    // Constructor that accepts a message
    public CustomerNotFoundException(String message) {
        super(message);
    }
}