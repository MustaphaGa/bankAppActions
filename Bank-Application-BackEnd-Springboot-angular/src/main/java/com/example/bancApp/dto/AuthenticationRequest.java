package com.example.bancApp.dto;

import lombok.*;

@Data

public class AuthenticationRequest {

    private String email;
    private String password;
}
