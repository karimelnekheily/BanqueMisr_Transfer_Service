package com.example.MoneyTransfer.controller;


import com.example.MoneyTransfer.dto.accountDto.AccountDto;
import com.example.MoneyTransfer.dto.accountDto.CreateAccountDto;
import com.example.MoneyTransfer.exception.custom.CustomerNotFoundException;
import com.example.MoneyTransfer.exception.response.ErrorDetails;
import com.example.MoneyTransfer.security.JwtUtils;
import com.example.MoneyTransfer.service.impl.AccountService;
import com.example.MoneyTransfer.service.impl.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.CrossOrigin;
import javax.security.auth.login.AccountNotFoundException;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/account")
@RequiredArgsConstructor
@Validated
public class AccountController {


    private final AccountService accountService;



    @Operation(summary = "Create new Sub Account for current customer")
    @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = AccountDto.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema(implementation = ErrorDetails.class), mediaType = "application/json")})
    @PostMapping
    public AccountDto createSubAccount(@Valid @RequestBody CreateAccountDto accountDTO) throws CustomerNotFoundException {

        return this.accountService.createSubAccount(accountDTO);
    }

    @Operation(summary = "Get account balance")
    @ApiResponse(responseCode = "200", description = "Balance retrieved successfully",
            content = @Content(schema = @Schema(implementation = Double.class)))
    @ApiResponse(responseCode = "404", description = "Account not found",
            content = @Content(schema = @Schema(implementation = ErrorDetails.class)))
    @GetMapping("/{accountNumber}/balance")
    public ResponseEntity<Double> getBalance(@PathVariable String accountNumber)
            throws AccountNotFoundException, CustomerNotFoundException {
        Double balance = accountService.getBalance(accountNumber);
        return ResponseEntity.ok(balance);
    }




}
