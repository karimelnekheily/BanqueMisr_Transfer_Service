package com.example.MoneyTransfer.dto.transactionDto;


import com.example.MoneyTransfer.enums.Currency;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SendMoneyByAccNumber {

    /*@NotNull
    private String accNumber;*/

    @NotNull
    private String SenderAccNumber;
    private String ReceiverAccNumber;

    @NotNull
    private Double amount;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Currency sendCurrency;

    /*@NotNull
    @Enumerated(EnumType.STRING)
    private Currency receiveCurrency;*/
}
