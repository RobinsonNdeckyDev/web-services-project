package com.example.webServices.mappers;

import com.example.webServices.dtos.LivreDTO;
import com.example.webServices.models.Livre;

public interface LivreMapper {
    public static LivreDTO toDTO(Livre livre) {
        return new LivreDTO(
                livre.getId(),
                livre.getTitre(),
                livre.getAuteur(),
                livre.getIsbn(),
                livre.isEstDisponible()
        );
    }

    public static Livre toEntity(LivreDTO dto) {
        Livre livre = new Livre();
        livre.setId(dto.getId());
        livre.setTitre(dto.getTitre());
        livre.setAuteur(dto.getAuteur());
        livre.setIsbn(dto.getIsbn());
        livre.setEstDisponible(dto.isEstDisponible());
        return livre;
    }
}
