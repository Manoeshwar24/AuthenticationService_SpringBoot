package com.example.authenticationservice.dtos;

import com.example.authenticationservice.models.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PutRoleRequestDTO {
    private long id;
    @JsonProperty("roleList")
    private List<Role> roles;
}
