package com.example.authenticationservice.controllers;

import com.example.authenticationservice.dtos.LoginRequestDTO;
import com.example.authenticationservice.dtos.LoginResponseDTO;
import com.example.authenticationservice.dtos.SignUpRequestDTO;
import com.example.authenticationservice.dtos.SignUpResponseDTO;
import com.example.authenticationservice.exceptions.UserAlreadyExistsException;
import com.example.authenticationservice.exceptions.UserDoesNotExistException;
import com.example.authenticationservice.exceptions.WrongPasswordException;
import com.example.authenticationservice.models.User;
import com.example.authenticationservice.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController()
@RequestMapping("/auth")
public class AuthenticationController {
    private UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/sign_up")
    public ResponseEntity<SignUpResponseDTO> signUp(@RequestBody SignUpRequestDTO signUpRequestDTO) throws UserAlreadyExistsException {
        //create User object from the request DTO
        User toBeCreatedUser = signUpRequestDTO.toUser();

        //create responseDTO with the result of the UserService
        SignUpResponseDTO signUpResponseDTO = new SignUpResponseDTO();
        //call UserService with created user object
        if(userService.signUpUser(toBeCreatedUser)) {
            signUpResponseDTO.fromUser(toBeCreatedUser);
            signUpResponseDTO.setResponseMessage("User successfully registered!");
        }

        return new ResponseEntity<>(signUpResponseDTO, HttpStatus.CREATED);
    }

    @PostMapping("login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO)
    throws UserDoesNotExistException, WrongPasswordException {
        //create User object from the request DTO
        User userToLogin = loginRequestDTO.toUser();
        //call the service to try and login
        String jwtToken = userService.loginUser(userToLogin);

        //response DTO
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
        loginResponseDTO.setToken(jwtToken);
        loginResponseDTO.setResponseMessage("User successfully logged in!");

        return new ResponseEntity<>(loginResponseDTO, HttpStatus.OK);
    }
}
