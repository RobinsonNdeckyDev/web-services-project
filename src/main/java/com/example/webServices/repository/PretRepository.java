package com.example.webServices.repository;

import com.example.webServices.models.Livre;
import com.example.webServices.models.Pret;
import com.example.webServices.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PretRepository extends JpaRepository<Pret, Long> {
    // Méthode pour récupérer les prêts d'un utilisateur
    List<Pret> findByUser_Id(Long userId);
}
