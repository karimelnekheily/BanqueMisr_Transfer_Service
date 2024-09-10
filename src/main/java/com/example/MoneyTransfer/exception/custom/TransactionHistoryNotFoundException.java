package com.example.MoneyTransfer.exception.custom;

public class TransactionHistoryNotFoundException extends RuntimeException {
    public TransactionHistoryNotFoundException(String message) {
        super(message);
    }
}
