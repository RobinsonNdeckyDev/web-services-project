package com.example.webServices.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LivreCreateRequestDTO {
    private String titre;
    private String auteur;
    private String isbn;
    private boolean estDisponible;
}
