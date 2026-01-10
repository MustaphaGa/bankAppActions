package com.example.bancApp.controllers;

import com.example.bancApp.dto.TransactionDto;
import com.example.bancApp.services.TransactionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
@Tag(name = "transaction")

public class TransactionController {

    private final TransactionService transactionService;
    @PostMapping("/")
    public ResponseEntity<Integer> save(
            @RequestBody TransactionDto TransactionDto
    ){
        return ResponseEntity.ok(transactionService.save(TransactionDto));
    }

    @GetMapping("/")
    public ResponseEntity<List<TransactionDto>> findAll(){
        return ResponseEntity.ok(transactionService.findAll());
    }
    @GetMapping("/{transaction-id}")
    public ResponseEntity<TransactionDto> findById(
            @PathVariable("transaction-id") Integer transactionId
    ){
        return ResponseEntity.ok(transactionService.findById(transactionId));

    }


    @GetMapping("/users/{user-id}")
    public ResponseEntity<List<TransactionDto>> findAllByUserId(
            @PathVariable("user-id") Integer userId
    ){
        return ResponseEntity.ok(transactionService.findAllByUserId(userId));

    }
    @DeleteMapping("/{transaction-id}")
    public ResponseEntity<Integer> delete(
            @PathVariable("transaction-id") Integer transactionId

    ){ transactionService.delete(transactionId);
        return ResponseEntity.accepted().build();

    }
}
