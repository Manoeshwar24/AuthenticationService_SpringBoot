package com.example.authenticationservice.services;

import com.example.authenticationservice.models.User;
import com.example.authenticationservice.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User signUpUser(User toBeCreatedUser) {

        return null;
    }
}
