package com.example.bancApp.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Address extends AbstractEntity {

    private  String street;
    private  String county;
    private  String city;
    private  Integer zipCode;
    private  Integer houseNumber;

    @OneToOne
    @JoinColumn(name = "id_user")

    private User user;





}
