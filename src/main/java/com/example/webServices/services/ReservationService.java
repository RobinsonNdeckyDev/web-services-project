package com.example.webServices.services;

import com.example.webServices.dtos.ReservationDTO;
import com.example.webServices.mappers.ReservationMapper;
import com.example.webServices.models.Reservation;
import com.example.webServices.repository.ReservationRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<ReservationDTO> getAllReservations() {
        return reservationRepository.findAll().stream()
                .map(ReservationMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ReservationDTO getReservationById(Long id) {
        return reservationRepository.findById(id)
                .map(ReservationMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Réservation non trouvée"));
    }

    public List<ReservationDTO> getReservationsByUserId(Long userId) {
        return reservationRepository.findByUser_Id(userId).stream()
                .map(ReservationMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ReservationDTO ajouterReservation(ReservationDTO reservationDTO) {
        Reservation reservation = ReservationMapper.toEntity(reservationDTO);
        return ReservationMapper.toDTO(reservationRepository.save(reservation));
    }

    public ReservationDTO modifierReservation(Long id, ReservationDTO reservationDTO) {
        Reservation existingReservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Réservation non trouvée"));
        Reservation reservation = ReservationMapper.toEntity(reservationDTO);
        existingReservation.setDateDebut(reservation.getDateDebut());
        existingReservation.setDateFin(reservation.getDateFin());
        existingReservation.setStatut(reservation.getStatut());
        return ReservationMapper.toDTO(reservationRepository.save(existingReservation));
    }

    public void supprimerReservation(Long id) {
        reservationRepository.deleteById(id);
    }
}
