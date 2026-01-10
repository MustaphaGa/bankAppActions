package com.example.bancApp.services;

import com.example.bancApp.dto.TransactionDto;
import com.example.bancApp.models.Transaction;

import java.util.List;

public interface TransactionService extends AbstractService<TransactionDto>{

    List<TransactionDto> findAllByUserId(Integer id);
}
