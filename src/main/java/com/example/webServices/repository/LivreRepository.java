package com.example.webServices.repository;

import com.example.webServices.models.Livre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LivreRepository extends JpaRepository<Livre, Long> {
    // Méthode pour récupérer les livres disponibles
    List<Livre> findByEstDisponible(boolean disponible);
}
