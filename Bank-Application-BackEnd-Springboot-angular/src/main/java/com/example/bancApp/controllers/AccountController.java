package com.example.bancApp.controllers;

import com.example.bancApp.dto.AccountDto;
import com.example.bancApp.services.AccountService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
@Tag(name = "account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/")
    public ResponseEntity<Integer> save(
            @RequestBody AccountDto accountDto
            ){
        return ResponseEntity.ok(accountService.save(accountDto));
    }

    @GetMapping("/")
    public ResponseEntity<List<AccountDto>> findAll(){
        return ResponseEntity.ok(accountService.findAll());
    }
    @GetMapping("/{account-id}")
    public ResponseEntity<AccountDto> findById(
            @PathVariable("account-id") Integer accountId
    ){ return ResponseEntity.ok(accountService.findById(accountId));

    }
    @DeleteMapping("/{account-id}")
    public ResponseEntity<Integer> delete(
            @PathVariable("account-id") Integer accountId

    ){ accountService.delete(accountId);
        return ResponseEntity.accepted().build();

    }
}
