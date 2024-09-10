package com.example.MoneyTransfer.dto.authDto;


import com.example.MoneyTransfer.enums.Country;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Data
public class RegisterCustomerRequest {

    @NotBlank
    private String name;

    @Email(message = "wrong email format")
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 6)
    private String password;

    @NotNull
    @Size(min = 6)
    private String confirmPassword;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Country country;

    @NotNull
    private LocalDate birthDate;


    //country , DOB
}
