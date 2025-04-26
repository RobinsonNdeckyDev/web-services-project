package com.example.webServices.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    private String email;
    private String nom;
    private RoleUtilisateur role;
}
