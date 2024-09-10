package com.example.MoneyTransfer.security;


  import com.example.MoneyTransfer.dto.CustomerDto.CustomerDto;
  import com.example.MoneyTransfer.dto.authDto.LoginRequestDTO;
  import com.example.MoneyTransfer.dto.authDto.LoginResponseDTO;
  import com.example.MoneyTransfer.dto.authDto.RegisterCustomerRequest;
import com.example.MoneyTransfer.exception.custom.CustomerAlreadyExistsException;

public interface IAuthenticator {
    /**
     * Account Creation
     * @param createCustomerDTO customer details
     * @return registered customer
     * @throws CustomerAlreadyExistsException if customer already exist
     */
    CustomerDto register(RegisterCustomerRequest createCustomerDTO) throws  CustomerAlreadyExistsException;

    /**
     * login a customer
     *
     * @param loginRequestDTO login details
     * @return login response
     */
    LoginResponseDTO login(LoginRequestDTO loginRequestDTO) ;

    void logout(String token);
}
