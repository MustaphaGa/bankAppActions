package com.example.bancApp.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Transaction extends AbstractEntity {

    private BigDecimal amount;

    private String destinationIban;

    @Column(updatable = false)
    private LocalDate transactionDate;

    @Enumerated(EnumType.STRING)
    private  TransactionType type;


    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;
}
