package com.example.authenticationservice.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ErrorResponseDTO {
    private String message;
    private LocalDateTime createdTime;
}
