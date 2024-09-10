package com.example.MoneyTransfer.service;


import com.example.MoneyTransfer.dto.transactionDto.SendMoneyByAccNumber;
import com.example.MoneyTransfer.dto.transactionDto.TransferResponse;

public interface ITransfer {

    public TransferResponse transferByAccountNumber(SendMoneyByAccNumber sendMoneyByAccNumber);
}
