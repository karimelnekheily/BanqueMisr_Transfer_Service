package com.example.MoneyTransfer.repository;

import com.example.MoneyTransfer.model.Customer;
import com.example.MoneyTransfer.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository  extends JpaRepository<Transaction, Integer> {
    Page<Transaction> findByCustomer(Customer customer, Pageable pageable);
    Page<Transaction> findBySenderOrReceiver(Customer sender, Customer receiver, Pageable pageable);


}
