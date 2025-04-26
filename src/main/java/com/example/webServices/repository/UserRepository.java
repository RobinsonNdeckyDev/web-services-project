package com.example.webServices.repository;

import com.example.webServices.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Trouver un utilisateur par email (pour l'authentification)
    Optional<User> findByEmail(String email);
}
