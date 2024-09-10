package com.example.MoneyTransfer.dto.transactionDto;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SendMoneyByEmail {

    @NotNull
    private String username;
    @NotNull
    private Double amount;
}
