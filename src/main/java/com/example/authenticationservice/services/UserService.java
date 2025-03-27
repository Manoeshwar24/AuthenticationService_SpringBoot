package com.example.authenticationservice.services;

import com.example.authenticationservice.exceptions.TokenInvalidException;
import com.example.authenticationservice.exceptions.UserAlreadyExistsException;
import com.example.authenticationservice.exceptions.UserDoesNotExistException;
import com.example.authenticationservice.exceptions.WrongPasswordException;
import com.example.authenticationservice.models.User;
import com.example.authenticationservice.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private JWTTokenService jwtTokenService;
    private SessionService sessionService;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
                       JWTTokenService jwtTokenService, SessionService sessionService) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtTokenService = jwtTokenService;
        this.sessionService = sessionService;
    }

    public boolean signUpUser(User toBeCreatedUser) throws UserAlreadyExistsException {
        //check if the user's email already exists
        if(userRepository.findByEmail(toBeCreatedUser.getEmail()).isPresent()){
            throw new UserAlreadyExistsException("User with mail '" + toBeCreatedUser.getEmail() + "' already exists!");
        }

        createNewUser(toBeCreatedUser);
        return true;
    }

    private void createNewUser(User toBeCreatedUser) {
        //encrypt the password before saving in the DB
        toBeCreatedUser.setPassword(bCryptPasswordEncoder.encode(toBeCreatedUser.getPassword()));

        //update the created and updated at
        toBeCreatedUser.setCreatedAt(LocalDateTime.now());
        toBeCreatedUser.setUpdatedAt(LocalDateTime.now());

        //persist the user data in the db
        User createdUser = userRepository.save(toBeCreatedUser);
    }

    public String loginUser(String inputEmail, String inputPassword, String ipAddress)
            throws UserDoesNotExistException, WrongPasswordException {
        //check if user exists in the DB
        Optional<User> loggedInUser = userRepository.findByEmail(inputEmail);
        if(loggedInUser.isPresent()) {
            //validate the inputPassword
            String dbPassword = loggedInUser.get().getPassword();
            if(!bCryptPasswordEncoder.matches(inputPassword, dbPassword)){
                throw new WrongPasswordException("Password is wrong!");
            }
            else {
                //check if token already exists for this ipAddress
                String jwtToken = jwtTokenService.generateToken(loggedInUser.get().getId(), inputEmail, ipAddress);

                //create a session in the DB for the current login
                sessionService.createOrUpdateSession(loggedInUser.get(), ipAddress ,jwtToken);

                return jwtToken;
            }
        }else {
            throw new UserDoesNotExistException("User does not exist!");
        }
    }

    public String logoutOfAllDevices(String jwtToken) throws TokenInvalidException {
        if(jwtTokenService.validateToken(jwtToken)){
            return "Logged out of all devices!";
        }
        else{
            throw new TokenInvalidException("Token is Invalid! Please login again.");
        }
    }
}
