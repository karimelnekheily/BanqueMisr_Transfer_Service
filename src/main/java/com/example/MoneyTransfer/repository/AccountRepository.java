package com.example.MoneyTransfer.repository;

import com.example.MoneyTransfer.model.Account;
import com.example.MoneyTransfer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,Long>   {
    Optional<Account> findByAccountNumberAndCustomer(String accountNumber, Customer customer);

    Optional<Account> findByAccountNumber(String accountNumber);

}
