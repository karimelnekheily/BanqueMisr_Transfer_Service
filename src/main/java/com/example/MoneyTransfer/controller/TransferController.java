package com.example.MoneyTransfer.controller;

import com.example.MoneyTransfer.dto.transactionDto.SendMoneyByAccNumber;
import com.example.MoneyTransfer.dto.transactionDto.TransferResponse;
import com.example.MoneyTransfer.service.impl.TransferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transfer")
@Tag(name = "Transfer Controller", description = "Transfer Controller")
@RequiredArgsConstructor
@Validated
@CrossOrigin(origins = "*")
public class TransferController{

    private final TransferService transferService;
    @CrossOrigin
    @Operation(summary = "transfer money by account number ")

    @PostMapping("/account")
    public ResponseEntity<TransferResponse> transferByAccountNumber(
            @Valid @RequestBody SendMoneyByAccNumber sendMoneyByAccNumber) {
        TransferResponse response = transferService.transferByAccountNumber(sendMoneyByAccNumber);
        return ResponseEntity.ok(response);
    }

}
