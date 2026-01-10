package com.example.bancApp.dto;

import com.example.bancApp.models.Account;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder

public class AccountDto {

    private Integer id;
    private  String iban;
    private UserDto userDto;
    public static AccountDto fromEntity(Account account){
        return
                AccountDto.builder()
                .id(account.getId())
                .iban(account.getIban())
                .userDto(UserDto.fromEntity(account.getUser()))
                .build();
    }
    public static Account toEntity(AccountDto accountDto){
        return  Account.builder()
                .id(accountDto.getId())
                .iban(accountDto.getIban())
                .user(UserDto.toEntity(accountDto.getUserDto()))
                .build();

    }

}
