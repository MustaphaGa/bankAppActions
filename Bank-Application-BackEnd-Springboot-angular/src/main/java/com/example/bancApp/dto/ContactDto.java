package com.example.bancApp.dto;

import com.example.bancApp.models.Contact;
import com.example.bancApp.models.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContactDto {

    private  Integer id;
    @NotNull(message = "le nom ne doit pas etre vide")
    @NotBlank(message = "le nom ne doit pas etre vide")
    @NotEmpty(message = "le nom ne doit pas etre vide")
    private  String firstName;
    @NotNull(message = "le nom ne doit pas etre vide")
    @NotBlank(message = "le nom ne doit pas etre vide")
    @NotEmpty(message = "le nom ne doit pas etre vide")
    private  String lastName;
    @NotNull(message = " email ne doit pas etre vide")
    @NotBlank(message = "email ne doit pas etre vide")
    @NotEmpty(message = "email ne doit pas etre vide")
    private  String email;

    private  String iban ;
    private Integer userId;



    public static ContactDto fromEntity(Contact contact){
        return ContactDto.builder()
                .id(contact.getId())
                .firstName(contact.getFirstName())
                .lastName(contact.getLastName())
                .iban(contact.getIban())
                .email(contact.getEmail())
                .userId(contact.getUser().getId())
                .build();
    }
    public static Contact toEntity(ContactDto contactDto){
        return Contact.builder()
                .id(contactDto.getId())
                .firstName(contactDto.getFirstName())
                .lastName(contactDto.getLastName())
                .iban(contactDto.getIban())
                .email(contactDto.getEmail())
                .user(
                        User.builder()
                                .id(contactDto.getUserId())
                                .build()
                )                .build();
    }


}
