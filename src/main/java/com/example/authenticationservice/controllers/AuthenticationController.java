package com.example.authenticationservice.controllers;

import com.example.authenticationservice.dtos.GetLoginRequestDTO;
import com.example.authenticationservice.dtos.GetLoginResponseDTO;
import com.example.authenticationservice.dtos.GetSignUpRequestDTO;
import com.example.authenticationservice.dtos.GetSignUpResponseDTO;
import com.example.authenticationservice.exceptions.UserCannotBeRegisteredException;
import com.example.authenticationservice.models.User;
import com.example.authenticationservice.services.UserService;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/auth")
public class AuthenticationController {
    private UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/signup")
    public GetSignUpResponseDTO signUp(@RequestBody GetSignUpRequestDTO getSignUpRequestDTO){
        //create User object from the request DTO
        User toBeCreatedUser = getSignUpRequestDTO.toUser();
        //call UserService with created user object
        User createdUser = userService.signUpUser(toBeCreatedUser);
        //create responseDTO with the result of the UserService
        GetSignUpResponseDTO getSignUpResponseDTO = new GetSignUpResponseDTO();
        getSignUpResponseDTO.fromUser(createdUser);
        getSignUpResponseDTO.setResponseMessage("User successfully registered!");

        return getSignUpResponseDTO;
    }

    @GetMapping
    public GetLoginResponseDTO login(@RequestBody GetLoginRequestDTO getLoginRequestDTO){

        return null;
    }
}
