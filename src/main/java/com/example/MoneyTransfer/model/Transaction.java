package com.example.MoneyTransfer.model;

  import com.example.MoneyTransfer.dto.transactionDto.TransferResponse;
  import com.example.MoneyTransfer.enums.Currency;
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
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Column(nullable = false)
    private boolean status;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "senderId", nullable = false)
    private Customer sender;

    @ManyToOne
    @JoinColumn(name = "receiverId", nullable = false)
    private Customer receiver;

    @ManyToOne
    @JoinColumn(name = "senderAccountId", nullable = false)
    private Account senderAccount;

    @ManyToOne
    @JoinColumn(name = "receiverAccountId", nullable = false)
    private Account receiverAccount;

    @CreationTimestamp
    private LocalDateTime timestamp;


    public TransferResponse toResponse() {
        return TransferResponse.builder()
                .transactionId(this.id)
                .fromAccount(this.senderAccount.getAccountNumber())
                .toAccount(this.receiverAccount.getAccountNumber())
                .amount(this.amount)
                .status(this.status)
                .timestamp(this.timestamp)
                .build();
    }


}
