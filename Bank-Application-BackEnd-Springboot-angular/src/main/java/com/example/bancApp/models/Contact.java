package com.example.bancApp.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Contact extends AbstractEntity{



    private  String firstName;
    private  String lastName;
    private  String iban ;
    private  String email;



    @ManyToOne
    @JoinColumn(name = "id_user")
    private  User user;
}
