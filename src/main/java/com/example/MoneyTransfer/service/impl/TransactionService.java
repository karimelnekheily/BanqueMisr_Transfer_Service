package com.example.MoneyTransfer.service.impl;

import com.example.MoneyTransfer.dto.transactionDto.AllTransactions;
import com.example.MoneyTransfer.dto.transactionDto.TransferResponse;
import com.example.MoneyTransfer.exception.custom.CustomerNotFoundException;
import com.example.MoneyTransfer.exception.custom.TransactionHistoryNotFoundException;
import com.example.MoneyTransfer.model.Customer;
import com.example.MoneyTransfer.model.Transaction;
import com.example.MoneyTransfer.repository.CustomerRepository;
import com.example.MoneyTransfer.repository.TransactionRepository;
import com.example.MoneyTransfer.service.ITransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService implements ITransaction {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private CustomerRepository customerRepository;


    public TransactionService(TransactionRepository transactionRepository, CustomerRepository customerRepository) {
        this.transactionRepository = transactionRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public AllTransactions getHistory(Pageable pageable) throws CustomerNotFoundException, TransactionHistoryNotFoundException {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Customer customer = customerRepository.findCustomerByEmail(email)
                .orElseThrow(() -> new CustomerNotFoundException("Logged-in customer not found"));

        Page<Transaction> transactionsPage = transactionRepository.findBySenderOrReceiver(customer, customer, pageable);

        if (transactionsPage.isEmpty()) {
            throw new TransactionHistoryNotFoundException("No transactions found for the logged-in customer");
        }

        List<TransferResponse> transferResponses = transactionsPage.getContent().stream()
                .map(Transaction::toResponse)
                .collect(Collectors.toList());

        return new AllTransactions(transferResponses);
    }
}