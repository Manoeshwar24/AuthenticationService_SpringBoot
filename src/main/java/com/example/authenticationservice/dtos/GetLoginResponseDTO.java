package com.example.authenticationservice.dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
public class GetLoginResponseDTO {
    private String token;
    private String username;
    private String email;
    private String responseMessage;
}
