package com.example.MoneyTransfer.model;


import com.example.MoneyTransfer.dto.CustomerDto.CustomerDto;
import com.example.MoneyTransfer.dto.authDto.RegisterCustomerResponse;
import com.example.MoneyTransfer.enums.Country;
import com.example.MoneyTransfer.enums.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Pattern(regexp = "^01[0125][0-9]{8}$")
    private String phoneNumber;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private Country country;
    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Builder.Default
    private Set<Account> accounts = new HashSet<>();

//################################

    @OneToMany(mappedBy = "sender")
    private List<Transaction> sentTransactions;

    @OneToMany(mappedBy = "receiver")
    private List<Transaction> receivedTransactions;

    @OneToMany(mappedBy = "favouriteCustomer")
    private List<Favourite> favourites;


    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions;


    public RegisterCustomerResponse toResponse() {
        return RegisterCustomerResponse.builder()
                .timestamp(LocalDateTime.now())
                .message("Customer Registered successfully")
                .details("Customer Registered Successfully")
                .httpStatus(HttpStatus.CREATED)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();
    }

    public CustomerDto toDTO() {
        return CustomerDto.builder()
                .id(this.id)
                .name(this.name)
                .email(this.email)
                .gender(gender)
                .phoneNumber(phoneNumber)
                .birthDate(birthDate)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .accounts(this.accounts.stream().map(Account::toDTO)
                        .collect(Collectors.toSet()))
                .build();
    }


    public CustomerDto toDto() {
        return CustomerDto.builder()
                .id(this.id)
                .name(this.name)
                .email(this.email)
                .gender(gender)
                .phoneNumber(phoneNumber)
                .birthDate(birthDate)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();
    }

}