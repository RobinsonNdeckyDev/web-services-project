package com.example.webServices.repository;

import com.example.webServices.models.Livre;
import com.example.webServices.models.Reservation;
import com.example.webServices.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    // Méthode pour récupérer les réservations d'un utilisateur
    List<Reservation> findByUser_Id(Long userId);
}
