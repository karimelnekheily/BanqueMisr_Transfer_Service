package com.example.MoneyTransfer.dto.favouriteDto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data

public class CreateFavourite {

    @NotNull
    private String accountNumber;

    @NotNull
    private String recipientName;



}
