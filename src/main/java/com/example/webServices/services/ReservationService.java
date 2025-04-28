package com.example.webServices.services;

import com.example.webServices.dtos.ReservationCreateRequestTDO;
import com.example.webServices.dtos.ReservationDTO;
import com.example.webServices.mappers.ReservationMapper;
import com.example.webServices.models.Livre;
import com.example.webServices.models.Reservation;
import com.example.webServices.models.User;
import com.example.webServices.repository.LivreRepository;
import com.example.webServices.repository.ReservationRepository;
import com.example.webServices.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final LivreRepository livreRepository;

    public ReservationService(ReservationRepository reservationRepository, UserRepository userRepository, LivreRepository livreRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.livreRepository = livreRepository;
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

    public ReservationDTO ajouterReservation(ReservationCreateRequestTDO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        Livre livre = livreRepository.findById(dto.getLivreId())
                .orElseThrow(() -> new RuntimeException("Livre non trouvé"));

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setLivre(livre);
        reservation.setDateDebut(dto.getDateDebut());
        reservation.setDateFin(dto.getDateFin());
        reservation.setStatut(dto.getStatut());

        Reservation saved = reservationRepository.save(reservation);
        return ReservationMapper.toDTO(saved);
    }

    public ReservationDTO modifierReservation(Long id, ReservationCreateRequestTDO dto) {
        Reservation existing = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Réservation non trouvée"));

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        Livre livre = livreRepository.findById(dto.getLivreId())
                .orElseThrow(() -> new RuntimeException("Livre non trouvé"));

        existing.setUser(user);
        existing.setLivre(livre);
        existing.setDateDebut(dto.getDateDebut());
        existing.setDateFin(dto.getDateFin());
        existing.setStatut(dto.getStatut());

        Reservation updated = reservationRepository.save(existing);
        return ReservationMapper.toDTO(updated);
    }

    public void supprimerReservation(Long id) {
        reservationRepository.deleteById(id);
    }
}
