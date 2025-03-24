package com.example.authenticationservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@Setter
public class Session extends BaseModel {
    private static int expiryTime = 30; //expiry time for each session

    private String ipAddress;
    private LocalDateTime lastLogin;
    private LocalDateTime startTime;
    private String token;
    private String device; //could be a separate class if needed

    @ManyToOne
    private User user;
}
