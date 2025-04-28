package com.example.webServices.mappers;

import com.example.webServices.dtos.PretDTO;
import com.example.webServices.models.Pret;

public interface PretMapper {
    public static PretDTO toDTO(Pret pret) {
        return new PretDTO(
                pret.getId(),
                pret.getUser().getId(),
                pret.getLivre().getId(),
                pret.getDatePret(),
                pret.getDateRetourPrevu(),
                pret.getDateRetourEffectif(),
                pret.isEstRetourne()
        );
    }

    public static Pret toEntity(PretDTO dto) {
        Pret pret = new Pret();
        pret.setDatePret(dto.getDatePret());
        pret.setDateRetourPrevu(dto.getDateRetourPrevu());
        pret.setDateRetourEffectif(dto.getDateRetourEffectif());
        pret.setEstRetourne(dto.isEstRetourne());
        return pret;
    }
}
