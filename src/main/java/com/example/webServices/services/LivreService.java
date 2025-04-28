package com.example.webServices.services;

import com.example.webServices.dtos.LivreCreateRequestDTO;
import com.example.webServices.dtos.LivreDTO;
import com.example.webServices.mappers.LivreMapper;
import com.example.webServices.models.Livre;
import com.example.webServices.repository.LivreRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class LivreService {

    private final LivreRepository livreRepository;

    public LivreService(LivreRepository livreRepository) {
        this.livreRepository = livreRepository;
    }

    public List<LivreDTO> getAllLivres() {
        return livreRepository.findAll().stream()
                .map(LivreMapper::toDTO)
                .collect(Collectors.toList());
    }

    public LivreDTO getLivreById(Long id) {
        return livreRepository.findById(id)
                .map(LivreMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Livre non trouvé"));
    }

    public List<LivreDTO> getLivresDisponibles() {
        return livreRepository.findByEstDisponible(true).stream()
                .map(LivreMapper::toDTO)
                .collect(Collectors.toList());
    }

    public LivreDTO ajouterLivre(LivreCreateRequestDTO livreDTO) {
        Livre livre = LivreMapper.toEntity(livreDTO);
        return LivreMapper.toDTO(livreRepository.save(livre));
    }

    public LivreDTO modifierLivre(Long id, LivreCreateRequestDTO livreDTO) {
        Livre existingLivre = livreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livre non trouvé"));
        Livre livre = LivreMapper.toEntity(livreDTO);
        existingLivre.setTitre(livre.getTitre());
        existingLivre.setAuteur(livre.getAuteur());
        existingLivre.setIsbn(livre.getIsbn());
        existingLivre.setEstDisponible(livre.isEstDisponible());
        return LivreMapper.toDTO(livreRepository.save(existingLivre));
    }

    /**
     * Marque un livre comme prêté à un user (ici, on simplifie et on ignore userId,
     * vous pourrez enrichir la logique pour enregistrer un prêt lié à un user).
     */
    public boolean preterLivre(Long userId, Long livreId) {
        Livre livre = livreRepository.findById(livreId)
                .orElseThrow(() -> new RuntimeException("Livre non trouvé"));
        if (!livre.isEstDisponible()) {
            return false;  // déjà prêté
        }
        livre.setEstDisponible(false);
        livreRepository.save(livre);
        return true;
    }

    /**
     * Marque un livre comme retourné (disponible) après prêt.
     */
    public boolean retournerLivre(Long userId, Long livreId) {
        Livre livre = livreRepository.findById(livreId)
                .orElseThrow(() -> new RuntimeException("Livre non trouvé"));
        if (livre.isEstDisponible()) {
            return false;  // déjà disponible
        }
        livre.setEstDisponible(true);
        livreRepository.save(livre);
        return true;
    }

    public void supprimerLivre(Long id) {
        livreRepository.deleteById(id);
    }
}
