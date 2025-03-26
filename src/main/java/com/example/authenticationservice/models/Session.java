package com.example.authenticationservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@Setter
public class Session extends BaseModel {

    private String ipAddress;
    private LocalDateTime lastLogin;
    private LocalDateTime expiryAt;
    private String token;

    @ManyToOne
    private User user;
    @Enumerated(EnumType.STRING)
    private SessionStatus sessionStatus;
}
