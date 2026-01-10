package com.example.bancApp.controllers;


import com.example.bancApp.dto.TransactionsSumDetails;
import com.example.bancApp.services.StatisticsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/statistics")
@RequiredArgsConstructor
@Tag(name = "statistic")

public class StatisticsController {
    private final StatisticsService statisticsService;

    @GetMapping("/by-date/{user-id}")
    public ResponseEntity<List<TransactionsSumDetails>> findSumTransactionByDate(
           @RequestParam("start-date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
           @RequestParam("end-date")  @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            @PathVariable("user-id") Integer userId){
        return ResponseEntity.ok(statisticsService.findSumTransactionByDate(startDate, endDate,userId));

    }
   @GetMapping("/balance/{user-id}")
    public ResponseEntity<BigDecimal>  getAccountBalance(
            @PathVariable("user-id") Integer userId){
       return ResponseEntity.ok(statisticsService.getAccountBalance(userId));

   }
    @GetMapping("/highest-transfert/{user-id}")
     public ResponseEntity<BigDecimal> highestTransfert(
             @PathVariable("user-id") Integer userId){
       return ResponseEntity.ok(statisticsService.highestTransfert(userId));
    }

     @GetMapping("/highest-deposit/{user-id}")
    public ResponseEntity<BigDecimal> highestDeposit(
            @PathVariable("user-id") Integer userId){
        return ResponseEntity.ok(statisticsService.highestDeposit(userId));
    }
}
