package com.example.authenticationservice.dtos;

import com.example.authenticationservice.models.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDTO {
    private String email;
    private String password;

    public User toUser(){
        User user = new User();
        user.setEmail(this.getEmail());
        user.setPassword(this.getPassword());

        return user;
    }
}
