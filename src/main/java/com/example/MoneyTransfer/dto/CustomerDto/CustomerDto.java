package com.example.MoneyTransfer.dto.CustomerDto;

import com.example.MoneyTransfer.dto.accountDto.AccountDto;
import com.example.MoneyTransfer.enums.Gender;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;



@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CustomerDto {

    private Long id;

    private String name;

    @Email
    private String email;

    @Pattern(regexp = "^01[0125][0-9]{8}$")
    private String phoneNumber;

    private Gender gender;
    private LocalDate birthDate;

    private String username;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Set<AccountDto> accounts;










}
