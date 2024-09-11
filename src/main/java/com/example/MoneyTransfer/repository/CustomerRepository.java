package com.example.MoneyTransfer.repository;

import com.example.MoneyTransfer.dto.CustomerDto.LastCustomerDto;
import com.example.MoneyTransfer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    boolean existsByEmail(String email);
    boolean existsByName(String username);
    LastCustomerDto findFirstByOrderByCreatedAtDesc();


    Optional<Customer> findCustomerByEmail(String email);

    Optional<Customer> getCustomerByEmail(String email);


}
