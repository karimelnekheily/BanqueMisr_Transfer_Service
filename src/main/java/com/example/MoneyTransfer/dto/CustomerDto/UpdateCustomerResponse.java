package com.example.MoneyTransfer.dto.CustomerDto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatusCode;

import java.time.LocalDateTime;


@Data
@Builder
public class UpdateCustomerResponse {

    private LocalDateTime updatedAt;
    private String massage;
    private String details;
    private HttpStatusCode httpStatusCode;
    private  final String name;
    @Email
    private  final String email;
    @Pattern(regexp = "^01[0125][0-9]{8}$")
    private  final String phoneNumber;
}
