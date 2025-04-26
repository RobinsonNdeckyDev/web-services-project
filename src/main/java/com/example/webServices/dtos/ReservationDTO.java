package com.example.webServices.dtos;

import com.example.webServices.models.StatutReservation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {
    private Long id;
    private Long userId;
    private Long livreId;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private StatutReservation statut;
}
