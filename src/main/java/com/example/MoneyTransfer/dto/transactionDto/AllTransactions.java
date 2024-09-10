package com.example.MoneyTransfer.dto.transactionDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class AllTransactions {
    private List<TransferResponse> transactions;

}
