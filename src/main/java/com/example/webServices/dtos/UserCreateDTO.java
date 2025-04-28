package com.example.webServices.dtos;

import com.example.webServices.models.RoleUtilisateur;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDTO {
    private String nom;
    private String email;
    private RoleUtilisateur role;
}
