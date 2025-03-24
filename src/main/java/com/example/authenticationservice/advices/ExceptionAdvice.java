package com.example.authenticationservice.advices;

import com.example.authenticationservice.dtos.ErrorResponseDTO;
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
}
