package com.example.MoneyTransfer.dto.authDto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatusCode;

import java.awt.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponseDTO {
    private String message;
    private String token;
    private String tokenType;
    private HttpStatusCode status;

    private  int customerId;
     private  String email;
     private   String name;
     private  Double balance;



}
