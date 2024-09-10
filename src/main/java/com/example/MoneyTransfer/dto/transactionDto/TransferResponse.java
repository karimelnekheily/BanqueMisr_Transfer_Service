package com.example.MoneyTransfer.dto.transactionDto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TransferResponse {
    private long transactionId;
    private String fromAccount;
    private String toAccount;
    private double amount;
    private boolean status;
    private LocalDateTime timestamp;
}
