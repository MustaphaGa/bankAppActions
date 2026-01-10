package com.example.bancApp.services.serviceImpl;

import com.example.bancApp.dto.AccountDto;
import com.example.bancApp.exceptions.OperationNonPermittedExceptions;
import com.example.bancApp.models.Account;
import com.example.bancApp.repositories.AccountRepository;
import com.example.bancApp.services.AccountService;
import com.example.bancApp.validator.ObjectValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.iban4j.CountryCode;
import org.iban4j.Iban;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final ObjectValidator<AccountDto> validator;

    @Override
    public Integer save(AccountDto dto) {
        validator.validate(dto);
        Account account = AccountDto.toEntity(dto);
        boolean userHasAlreadyAccount = accountRepository.findByUserId(account.getUser().getId()).isPresent();
        if (userHasAlreadyAccount && account.getUser().isActive()) {
            throw new OperationNonPermittedExceptions(
                    "the user has already an account",
                    "Create Account",
                    "account service",
                    "account ceation"
            );
        }
//generate randomIban
        if (dto.getId() == null) {
            account.setIban(generateRandomIban());
        }
        return accountRepository.save(account).getId();
    }

    @Override
    public List<AccountDto> findAll() {
        return accountRepository.findAll()
                .stream()
                .map(AccountDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public AccountDto findById(Integer id) {
        return accountRepository.findById(id)
                .map(AccountDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("the user with id is not found" + id));
    }

    @Override
    public void delete(Integer id) {

        accountRepository.deleteById(id);

    }

    private String generateRandomIban() {
        //generate Iban
        String iban = Iban.random(CountryCode.DE).toFormattedString();

        //check  Iban if already existed

        boolean ibanExisted = accountRepository.findByIban(iban).isPresent();
        //generete andom iban

        if (ibanExisted) {
            generateRandomIban();
        }
        return iban;
    }
}