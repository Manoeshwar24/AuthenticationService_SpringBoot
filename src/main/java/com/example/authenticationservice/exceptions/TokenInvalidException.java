package com.example.authenticationservice.exceptions;

public class TokenInvalidException extends Exception {
    public TokenInvalidException(String message) {
        super(message);
    }
}
