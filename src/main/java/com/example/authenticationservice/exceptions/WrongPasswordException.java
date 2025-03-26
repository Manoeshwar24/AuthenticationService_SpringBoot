package com.example.authenticationservice.exceptions;

public class WrongPasswordException extends Exception {
    public WrongPasswordException(String message) {
        super(message);
    }
}
