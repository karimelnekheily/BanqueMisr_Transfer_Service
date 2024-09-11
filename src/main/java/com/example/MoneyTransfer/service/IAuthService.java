package com.example.MoneyTransfer.service;

import com.example.MoneyTransfer.dto.authDto.LoginRequestDTO;
import com.example.MoneyTransfer.dto.authDto.LoginResponseDTO;
import com.example.MoneyTransfer.dto.authDto.RegisterCustomerRequest;
import com.example.MoneyTransfer.dto.authDto.RegisterCustomerResponse;
import com.example.MoneyTransfer.exception.custom.CustomerAlreadyExistsException;
import com.example.MoneyTransfer.exception.custom.CustomerNotFoundException;
import org.springframework.transaction.annotation.Transactional;

public interface IAuthService {

    RegisterCustomerResponse register(RegisterCustomerRequest request) throws CustomerAlreadyExistsException;

    LoginResponseDTO login(LoginRequestDTO loginRequestDTO) throws CustomerNotFoundException;

    void logout( String token) throws CustomerNotFoundException;


    @Transactional
    void updatePassword(String oldPassword, String newPassword) throws CustomerNotFoundException;
}
