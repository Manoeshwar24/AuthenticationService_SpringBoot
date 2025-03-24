package com.example.authenticationservice.dtos;

import com.example.authenticationservice.models.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetSignUpResponseDTO {
    private String first_name;
    private String last_name;
    private String email;
    private String responseMessage;

    public void fromUser(User user){
        this.setFirst_name(user.getFirst_name());
        this.setLast_name(user.getLast_name());
        this.setEmail(user.getEmail());
    }
}
