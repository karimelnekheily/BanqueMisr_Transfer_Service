package com.example.MoneyTransfer.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Favourite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="userId", nullable=false)
    private Customer customer;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="favId", nullable=false)
    private Customer favouriteCustomer;

    @NonNull
    private  String AccountNumber;

    @NonNull
    private String RecipientName;


    @CreationTimestamp
    private LocalDateTime addedAt;
}
