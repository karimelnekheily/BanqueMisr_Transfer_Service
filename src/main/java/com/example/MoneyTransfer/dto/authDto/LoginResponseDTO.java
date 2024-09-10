package com.example.MoneyTransfer.dto.authDto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatusCode;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponseDTO {
    private String message;
    private String token;
    private String tokenType;
    private HttpStatusCode status;


}
