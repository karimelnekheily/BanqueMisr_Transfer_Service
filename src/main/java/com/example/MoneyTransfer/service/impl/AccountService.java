package com.example.MoneyTransfer.service.impl;

import com.example.MoneyTransfer.dto.accountDto.AccountDto;
import com.example.MoneyTransfer.dto.accountDto.CreateAccountDto;
import com.example.MoneyTransfer.exception.custom.CustomerNotFoundException;
import com.example.MoneyTransfer.model.Account;
import com.example.MoneyTransfer.model.Customer;
import com.example.MoneyTransfer.repository.AccountRepository;
import com.example.MoneyTransfer.repository.CustomerRepository;
import com.example.MoneyTransfer.service.IAccountService;
import com.example.MoneyTransfer.service.helper.AccountNumberGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;

@Service
@RequiredArgsConstructor
public class AccountService implements IAccountService {

    private final CustomerRepository customerRepository ;
    private final AccountRepository accountRepository ;
    @Override
    public AccountDto createSubAccount(CreateAccountDto accountDTO) throws CustomerNotFoundException {

        // Get the authenticated user's email
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String customerEmail = authentication.getName();

        // Find the customer by email
        Customer customer = this.customerRepository.findCustomerByEmail(customerEmail)
                .orElseThrow(() -> new CustomerNotFoundException("Customer with email " + customerEmail + " not found"));




        Account account = Account.builder()
                .accountNumber(AccountNumberGenerator.generateAccountNumber())
                .accountType(accountDTO.getAccountType())
                .accountName(accountDTO.getAccountName())
                .accountDescription(accountDTO.getAccountDescription())
                .currency(accountDTO.getCurrency())
                .balance(0.0)
                .customer(customer)
                .build();

        Account savedAccount = this.accountRepository.save(account);

        return savedAccount.toDTO();
    }

    @Override
    public Double getBalance(String accountNumber) throws AccountNotFoundException, CustomerNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String customerEmail = authentication.getName();

        Customer customer = customerRepository.findCustomerByEmail(customerEmail)
                .orElseThrow(() -> new CustomerNotFoundException("Customer with email " + customerEmail + " not found"));

        Account account = accountRepository.findByAccountNumberAndCustomer(accountNumber, customer)
                .orElseThrow(() -> new AccountNotFoundException("Account not found or does not belong to the current user"));

        return account.getBalance();
    }


}
