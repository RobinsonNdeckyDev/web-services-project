package com.example.webServices.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // génère getters, setters, toString, equals, hashCode
@NoArgsConstructor // génère constructeur sans argument
@AllArgsConstructor // génère constructeur avec tous les arguments
public class LivreDTO {
    private Long id;
    private String titre;
    private String auteur;
    private String isbn;
    private boolean estDisponible;
}
