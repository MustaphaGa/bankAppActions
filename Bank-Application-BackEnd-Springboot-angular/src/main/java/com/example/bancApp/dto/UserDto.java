package com.example.bancApp.dto;

import com.example.bancApp.models.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserDto {

    private Integer id;

    @NotNull(message = "le nom ne doit pas etre vide")
    @NotBlank(message = "le nom ne doit pas etre vide")
    @NotEmpty(message = "le nom ne doit pas etre vide")
    private  String firstName;

    @NotNull(message = "le prenom ne doit pas etre vide")
    @NotBlank(message = "le prenom ne doit pas etre vide")
    @NotEmpty(message = "le prenom ne doit pas etre vide")
    private  String lastName;

    @NotNull(message = "l email ne doit pas etre vide")
    @NotBlank(message = "l email ne doit pas etre vide")
    @NotEmpty(message = "l email ne doit pas etre vide")
    private  String email;
    @NotNull
    @NotBlank
    @NotEmpty
    @Size(min = 8,max = 16 , message ="le mot de passe doit etre entre 8 et  6 caracteres")
    private  String password;
    private  boolean active;
    private String iban;

public static UserDto fromEntity(User user){
    return UserDto.builder()
            .id(user.getId())
            .firstName(user.getFirstName())
            .lastName(user.getLastName())
            .email(user.getEmail())
            .active(user.isActive())
            .iban(user.getAccount()==null ? "" : user.getAccount().getIban())
            //.password(user.getPassword())
            .build();
}

public  static User toEntity(UserDto userDto){
    return User.builder()
            .id(userDto.getId())
            .firstName(userDto.getFirstName())
            .lastName(userDto.getLastName())
            .email(userDto.getEmail())
            .password(userDto.getPassword())
            .build();
}

}
