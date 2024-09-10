package com.example.MoneyTransfer.dto.accountDto;


import com.example.MoneyTransfer.enums.AccountType;
import com.example.MoneyTransfer.enums.Currency;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {

    @NotNull
    private Long id;

    @NotBlank
    @Size(min = 10, max = 20)
    private String accountNumber;

    @NotNull
    private AccountType accountType;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private Double balance;

    @NotNull
    private Currency currency;

    @NotBlank
    @Size(max = 50)
    private String accountName;

    @Size(max = 200)
    @NotNull
    private String accountDescription;

    @NotNull
    private Boolean active;

    @PastOrPresent
    private LocalDateTime createdAt;

    @PastOrPresent
    private LocalDateTime updatedAt;
}
