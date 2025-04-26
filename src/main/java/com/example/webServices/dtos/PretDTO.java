package com.example.webServices.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PretDTO {
    private Long id;
    private Long userId;
    private Long livreId;
    private LocalDate datePret;
    private LocalDate dateRetourPrevu;
    private LocalDate dateRetourEffectif;
    private boolean estRetourne;
}
