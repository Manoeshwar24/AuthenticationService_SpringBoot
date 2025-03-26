package com.example.authenticationservice.advices;

import com.example.authenticationservice.dtos.ErrorResponseDTO;
import com.example.authenticationservice.exceptions.UserAlreadyExistsException;
import com.example.authenticationservice.exceptions.UserDoesNotExistException;
import com.example.authenticationservice.exceptions.WrongPasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@ControllerAdvice
@RestController
public class ExceptionAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleException(Exception ex) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
        errorResponseDTO.setMessage(ex.getMessage());
        errorResponseDTO.setCreatedTime(LocalDateTime.now());

        return new ResponseEntity<>(errorResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDTO> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
        errorResponseDTO.setMessage(ex.getMessage());
        errorResponseDTO.setCreatedTime(LocalDateTime.now());

        return new ResponseEntity<>(errorResponseDTO, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(WrongPasswordException.class)
    public ResponseEntity<ErrorResponseDTO> handleWrongThreadException(WrongPasswordException ex) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
        errorResponseDTO.setMessage(ex.getMessage());
        errorResponseDTO.setCreatedTime(LocalDateTime.now());

        return new ResponseEntity<>(errorResponseDTO, HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler(UserDoesNotExistException.class)
    public ResponseEntity<ErrorResponseDTO> handleUserDoesNotExistException(UserDoesNotExistException ex) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
        errorResponseDTO.setMessage(ex.getMessage());
        errorResponseDTO.setCreatedTime(LocalDateTime.now());

        return new ResponseEntity<>(errorResponseDTO, HttpStatus.NOT_FOUND);
    }
}
