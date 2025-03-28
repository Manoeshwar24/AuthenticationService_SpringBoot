package com.example.authenticationservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class User extends BaseModel {
    private String first_name;
    private String last_name;
    private String email;
    private String password; //encrypted

    @ManyToMany(fetch = jakarta.persistence.FetchType.EAGER)
    private List<Role> roles = new ArrayList<>();
}
