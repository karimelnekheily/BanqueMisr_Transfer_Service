package com.example.MoneyTransfer.service.impl;

import com.example.MoneyTransfer.dto.transactionDto.SendMoneyByAccNumber;
import com.example.MoneyTransfer.dto.transactionDto.TransferResponse;
import com.example.MoneyTransfer.enums.Currency;
import com.example.MoneyTransfer.model.Customer;
import com.example.MoneyTransfer.model.Account;
import com.example.MoneyTransfer.model.Transaction;
import com.example.MoneyTransfer.repository.AccountRepository;
import com.example.MoneyTransfer.repository.TransactionRepository;
import com.example.MoneyTransfer.service.ITransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransferService implements ITransfer {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CurrencyExchangeService currencyExchangeService;

    @Override
    @Transactional
    public TransferResponse transferByAccountNumber(SendMoneyByAccNumber sendMoneyByAccNumber) {
        // Find sender account
        Account senderAccount = accountRepository.findByAccountNumber(sendMoneyByAccNumber.getSenderAccNumber())
                .orElseThrow(() -> new RuntimeException("Sender account not found"));

        // Find receiver account
        Account receiverAccount = accountRepository.findByAccountNumber(sendMoneyByAccNumber.getReceiverAccNumber())
                .orElseThrow(() -> new RuntimeException("Receiver account not found"));

        // Check if sender has sufficient balance
        if (senderAccount.getBalance() < sendMoneyByAccNumber.getAmount()) {
            throw new RuntimeException("Insufficient balance in sender's account");
        }

        // Perform currency conversion: Convert sender's currency to EGY
        double amountInEGY = currencyExchangeService.convertCurrency(
                sendMoneyByAccNumber.getAmount(),
                sendMoneyByAccNumber.getSendCurrency(),
                Currency.EGY  // Convert to EGY currency
        );

        // Update balances
        senderAccount.setBalance(senderAccount.getBalance() - amountInEGY);
        receiverAccount.setBalance(receiverAccount.getBalance() + amountInEGY);

        // Save updated account balances
        accountRepository.save(senderAccount);
        accountRepository.save(receiverAccount);

        // Create and save transaction
        Transaction transaction = Transaction.builder()
                .amount(sendMoneyByAccNumber.getAmount())
                .currency(sendMoneyByAccNumber.getSendCurrency())
                .status(true)
                .sender(senderAccount.getCustomer())
                .receiver(receiverAccount.getCustomer())
                .senderAccount(senderAccount)
                .receiverAccount(receiverAccount)
                .build();

        Transaction savedTransaction = transactionRepository.save(transaction);

        // Return transfer response
        return savedTransaction.toResponse();
    }

}