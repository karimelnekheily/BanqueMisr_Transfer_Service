package com.example.MoneyTransfer.service;

import com.example.MoneyTransfer.dto.accountDto.AccountDto;
import com.example.MoneyTransfer.dto.accountDto.CreateAccountDto;
import com.example.MoneyTransfer.exception.custom.CustomerNotFoundException;

import javax.security.auth.login.AccountNotFoundException;

public interface IAccountService {


    /**
     * Create a new account
     *
     * @param accountDTO the account to be created
     * @return the created account
     * @throws  if the account is not found
     */
    AccountDto createSubAccount(CreateAccountDto accountDTO) throws CustomerNotFoundException;

    Double getBalance(String accountNumber) throws AccountNotFoundException, CustomerNotFoundException;
}
