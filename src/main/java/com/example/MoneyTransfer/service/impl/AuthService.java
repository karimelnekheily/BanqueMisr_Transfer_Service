package com.example.MoneyTransfer.service.impl;


import com.example.MoneyTransfer.dto.authDto.LoginRequestDTO;
import com.example.MoneyTransfer.dto.authDto.LoginResponseDTO;
import com.example.MoneyTransfer.dto.authDto.RegisterCustomerRequest;
import com.example.MoneyTransfer.dto.authDto.RegisterCustomerResponse;
import com.example.MoneyTransfer.enums.AccountType;
import com.example.MoneyTransfer.enums.Currency;
import com.example.MoneyTransfer.exception.custom.CustomerAlreadyExistsException;
import com.example.MoneyTransfer.exception.custom.CustomerNotFoundException;
import com.example.MoneyTransfer.exception.custom.PasswordNotMatchException;
import com.example.MoneyTransfer.model.Account;
import com.example.MoneyTransfer.model.Customer;
import com.example.MoneyTransfer.repository.CustomerRepository;
import com.example.MoneyTransfer.security.JwtUtils;
//import jakarta.transaction.Transactional;
import com.example.MoneyTransfer.service.IAuthService;
import com.example.MoneyTransfer.service.helper.AccountNumberGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;


@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {

    private final CustomerRepository customerRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;






    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)

    public RegisterCustomerResponse register(RegisterCustomerRequest customerRequest) throws CustomerAlreadyExistsException, PasswordNotMatchException {

        if (Boolean.TRUE.equals(this.customerRepository.existsByEmail(customerRequest.getEmail()))) {
            throw new CustomerAlreadyExistsException("Customer with email " + customerRequest.getEmail() + " already exists");
        }
        if(customerRepository.existsByName(customerRequest.getName())) {
            throw new CustomerAlreadyExistsException("Customer with name " + customerRequest.getName() + " already exists");
        }

        if(!Objects.equals(customerRequest.getPassword(),customerRequest.getConfirmPassword())){
            throw new PasswordNotMatchException("Password not match");
        }


        Customer customer = Customer.builder()
                .name(customerRequest.getName())
                .email(customerRequest.getEmail())
                .password(this.passwordEncoder.encode(customerRequest.getPassword()))
                .country(customerRequest.getCountry())
                .birthDate(customerRequest.getBirthDate())
                  .build();


        Account account = Account.builder()
                .balance(1000.0)
                .accountType(AccountType.SAVINGS)
                .accountDescription("this is "+(AccountType.BUSINESS)+" Account" )
                .accountName(String.valueOf(AccountType.BUSINESS))
                .currency(Currency.EGY)
                .accountNumber(AccountNumberGenerator.generateAccountNumber())
                .customer(customer)
                .build();

        customer.getAccounts().add(account);
        Customer savedCustomer = this.customerRepository.save(customer);

        return savedCustomer.toResponse();

    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) throws CustomerNotFoundException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(), loginRequestDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtils.generateJwtToken(authentication);


        Customer customer =customerRepository.findCustomerByEmail(loginRequestDTO.getEmail())
                .orElseThrow(()-> new CustomerNotFoundException("Customer not found"+loginRequestDTO.getEmail()));

        Double balance =customer.getAccounts().stream().findFirst().map(Account::getBalance).orElse(0.0);

        return LoginResponseDTO.builder()
                .token(jwt)
                .message("Login Successful")
                .status(HttpStatus.ACCEPTED)
                .tokenType("Bearer")
                .customerId(Math.toIntExact(customer.getId()))
                .name(customer.getName())
                .email(customer.getEmail())
                .balance(balance)
                .build();
    }
    @Override
    @Transactional
    public void updatePassword( String oldPassword, String newPassword) throws     CustomerNotFoundException {
        // Validate password strength (optional)
        //validatePasswordStrength(newPassword);


      String email=SecurityContextHolder.getContext().getAuthentication().getName();
         Customer customer = customerRepository.findCustomerByEmail(email)
              .orElseThrow(() -> new CustomerNotFoundException(String.format("Customer with email %s not found", email)));

        if (!passwordEncoder.matches(oldPassword, customer.getPassword())) {
            throw new IllegalArgumentException("Old password is incorrect");
        }

        if (passwordEncoder.matches(newPassword, customer.getPassword())) {
            throw new IllegalArgumentException("New password cannot be the same as the old password");
        }

        customer.setPassword(passwordEncoder.encode(newPassword));
        customerRepository.save(customer);
    }

    private void validatePasswordStrength(String password) {
        // Example password strength validation
        if (password.length() < 6) {
            throw new IllegalArgumentException("Password must be at least 8 characters long");
        }
        // Add more validations as needed (e.g., uppercase, numbers, special characters)
    }

    @Override
    public void logout(String token) throws CustomerNotFoundException {
        jwtUtils.invalidJwtToken(token);
        SecurityContextHolder.clearContext();
    }
}
