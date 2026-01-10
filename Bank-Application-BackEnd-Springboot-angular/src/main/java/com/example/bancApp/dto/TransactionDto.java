package com.example.bancApp.dto;

import com.example.bancApp.models.Transaction;
import com.example.bancApp.models.TransactionType;
import com.example.bancApp.models.User;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class TransactionDto {

    private Integer id;

    @NotNull(message = "amount ne doit pas etre vide")
    @Positive
    @Max(value = 10000)
    @Min(value = 10)
    private BigDecimal amount;

    private String destinationIban;
    private LocalDate transactionDate;


    private TransactionType type;
    private Integer userId;
    private Integer contactId;


    public static TransactionDto fromEntity(Transaction transaction) {

        return TransactionDto.builder()
                .id(transaction.getId())
                .amount(transaction.getAmount())
                .type(transaction.getType())
                .destinationIban(transaction.getDestinationIban())
                .transactionDate(transaction.getTransactionDate())
                .userId(transaction.getUser().getId())
                .build();
    }

    public static Transaction toEntity(TransactionDto transactionDto) {
        return Transaction.builder()
                .id(transactionDto.getId())
                .amount(transactionDto.getAmount())
                .type(transactionDto.getType())
                .transactionDate(LocalDate.now())
                .destinationIban(transactionDto.getDestinationIban())
                .user(
                        User.builder()
                                .id(transactionDto.getUserId())
                                .build()
                )
                .build();
    }
}
