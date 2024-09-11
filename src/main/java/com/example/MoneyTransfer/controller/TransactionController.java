package com.example.MoneyTransfer.controller;


import com.example.MoneyTransfer.dto.transactionDto.AllTransactions;
import com.example.MoneyTransfer.exception.custom.CustomerNotFoundException;
import com.example.MoneyTransfer.service.ITransaction;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")

@RequestMapping("/api/transactions")
@Tag(name = "Transaction Controller", description = "Transaction Controller")
@RequiredArgsConstructor
@Validated
public class TransactionController {

    private final ITransaction transactionService;

    @CrossOrigin
    @Operation(summary = "get the transactions history of the logged in customer ")
    @GetMapping("/history")
    public ResponseEntity<AllTransactions> getTransactionHistory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) throws CustomerNotFoundException {
        Pageable pageable = PageRequest.of(page, size);
        AllTransactions transactions = transactionService.getHistory(pageable);
        return ResponseEntity.ok(transactions);
    }

}
