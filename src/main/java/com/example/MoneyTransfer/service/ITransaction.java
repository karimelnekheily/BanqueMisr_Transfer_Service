package com.example.MoneyTransfer.service;

import com.example.MoneyTransfer.dto.transactionDto.AllTransactions;
import com.example.MoneyTransfer.exception.custom.CustomerNotFoundException;
import org.springframework.data.domain.Pageable;


public interface ITransaction {

    AllTransactions getHistory(Pageable pageable) throws CustomerNotFoundException;

}
