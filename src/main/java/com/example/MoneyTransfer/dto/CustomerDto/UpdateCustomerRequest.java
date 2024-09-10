package com.example.MoneyTransfer.dto.CustomerDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@Builder
public class UpdateCustomerRequest {

    private  final String name;
    @Email
    private  final String email;
    @Pattern(regexp = "^01[0125][0-9]{8}$")
    private  final String phoneNumber;
}
