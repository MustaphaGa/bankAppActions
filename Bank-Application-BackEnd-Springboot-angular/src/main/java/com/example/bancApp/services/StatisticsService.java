package com.example.bancApp.services;

import com.example.bancApp.dto.TransactionsSumDetails;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface StatisticsService {

    List<TransactionsSumDetails> findSumTransactionByDate(LocalDate startDate, LocalDate endDate, Integer userId);
    BigDecimal getAccountBalance(Integer userId);
    BigDecimal highestTransfert(Integer userId);

    BigDecimal highestDeposit(Integer userId);

}
