package com.example.authenticationservice.dtos;

import com.example.authenticationservice.models.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetSignUpRequestDTO {
    private String first_name;
    private String last_name;
    private String email;
    private String phone;
    private String password; //how to get it from User? (encrypted or how)?

    public User toUser(){
        User user = new User();

        user.setFirst_name(this.getFirst_name());
        user.setLast_name(this.getLast_name());
        user.setEmail(this.getEmail());
        user.setPhoneNumber(this.getPhone());
        user.setPassword(this.getPassword());

        return user;
    }
}
