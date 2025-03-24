package com.example.authenticationservice.controllers;

import com.example.authenticationservice.dtos.PutRoleRequestDTO;
import com.example.authenticationservice.dtos.PutRoleResponseDTO;
import com.example.authenticationservice.services.RoleService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roles")
public class RoleController {
    private RoleService roleService;
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PutMapping("/{id}")
    public PutRoleResponseDTO updateRoleForUser(@PathVariable long id, @RequestBody PutRoleRequestDTO putRoleRequestDTO){

        return null;
    }
}
