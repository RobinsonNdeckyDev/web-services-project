package com.example.webServices.mappers;

import com.example.webServices.dtos.ReservationCreateRequestTDO;
import com.example.webServices.dtos.ReservationDTO;
import com.example.webServices.models.Reservation;

public interface ReservationMapper {
    public static ReservationDTO toDTO(Reservation reservation) {
        return new ReservationDTO(
                reservation.getId(),
                reservation.getUser().getId(),
                reservation.getLivre().getId(),
                reservation.getDateDebut(),
                reservation.getDateFin(),
                reservation.getStatut()
        );
    }

    public static Reservation toEntity(ReservationCreateRequestTDO dto) {
        Reservation reservation = new Reservation();
        reservation.setDateDebut(dto.getDateDebut());
        reservation.setDateFin(dto.getDateFin());
        reservation.setStatut(dto.getStatut());
        return reservation;
    }
}
