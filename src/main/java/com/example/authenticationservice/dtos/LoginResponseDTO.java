package com.example.authenticationservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDTO {
    private String token;
    private String responseMessage;
}
