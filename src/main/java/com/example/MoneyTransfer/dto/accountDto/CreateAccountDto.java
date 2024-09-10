package com.example.MoneyTransfer.dto.accountDto;

import com.example.MoneyTransfer.enums.AccountType;
import com.example.MoneyTransfer.enums.Currency;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateAccountDto {



    @NotNull
    private AccountType accountType;

    @NotNull
    private Currency currency;

    @NotBlank
    private String accountName;

    @NotBlank
    private String accountDescription;








}
