package com.example.authenticationservice.controllers;

import com.example.authenticationservice.dtos.LogOutResponseDTO;
import com.example.authenticationservice.services.JWTTokenService;
import com.example.authenticationservice.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;
    private JWTTokenService jwtTokenService;

    public UserController(UserService userService, JWTTokenService jwtTokenService) {
        this.userService = userService;
        this.jwtTokenService = jwtTokenService;
    }

    @PostMapping("/log_out/all-devices")
    public ResponseEntity<LogOutResponseDTO> logoutOfAllDevices(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            // Extract the JWT token from the "Authorization" header
            String jwtToken = authorizationHeader.replace("Bearer ", "");

            String responseMessage = userService.logoutOfAllDevices(jwtToken);
            LogOutResponseDTO logOutResponseDTO = new LogOutResponseDTO();
            logOutResponseDTO.setResponseMessage(responseMessage);
            return new ResponseEntity<>(logOutResponseDTO, HttpStatus.OK);
        } catch (Exception e) {
            LogOutResponseDTO logOutResponseDTO = new LogOutResponseDTO();
            logOutResponseDTO.setResponseMessage("Error logging out of all devices");
            return new ResponseEntity<>(logOutResponseDTO, HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("log_out/current-device")
    public ResponseEntity<LogOutResponseDTO> logOutOfSpecificDevice(@RequestHeader("Authorization") String authorizationHeader) {
        try{
            //extract the jwt Token from the authorization header
            String jwtToken = authorizationHeader.replace("Bearer ", "");
            //call the service to log out of the specific device
            String responseMessage = userService.logoutOfSpecificDevice(jwtToken);

            //create response DTO
            LogOutResponseDTO logOutResponseDTO = new LogOutResponseDTO();
            logOutResponseDTO.setResponseMessage(responseMessage);
            return new ResponseEntity<>(logOutResponseDTO, HttpStatus.OK);
        }
        catch (Exception e){
            LogOutResponseDTO logOutResponseDTO = new LogOutResponseDTO();
            logOutResponseDTO.setResponseMessage("Cannot log out of the selected device");
            return new ResponseEntity<>(logOutResponseDTO, HttpStatus.UNAUTHORIZED);
        }
    }

}
