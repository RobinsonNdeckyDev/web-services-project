package com.example.webServices.services;

import com.example.webServices.dtos.PretCreateRequestDTO;
import com.example.webServices.dtos.PretDTO;
import com.example.webServices.dtos.ReservationCreateRequestTDO;
import com.example.webServices.dtos.ReservationDTO;
import com.example.webServices.mappers.PretMapper;
import com.example.webServices.mappers.ReservationMapper;
import com.example.webServices.models.Livre;
import com.example.webServices.models.Pret;
import com.example.webServices.models.Reservation;
import com.example.webServices.models.User;
import com.example.webServices.repository.LivreRepository;
import com.example.webServices.repository.PretRepository;
import com.example.webServices.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PretService {

    private final PretRepository pretRepository;
    private final UserRepository userRepository;
    private final LivreRepository livreRepository;

    public PretService(PretRepository pretRepository, UserRepository userRepository, LivreRepository livreRepository) {
        this.pretRepository = pretRepository;
        this.userRepository = userRepository;
        this.livreRepository = livreRepository;
    }

    public List<PretDTO> getAllPrets() {
        return pretRepository.findAll().stream()
                .map(PretMapper::toDTO)
                .collect(Collectors.toList());
    }

    public PretDTO getPretById(Long id) {
        return pretRepository.findById(id)
                .map(PretMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Prêt non trouvé"));
    }

    public List<PretDTO> getPretsByUserId(Long userId) {
        return pretRepository.findByUser_Id(userId).stream()
                .map(PretMapper::toDTO)
                .collect(Collectors.toList());
    }

    public PretDTO ajouterPret(PretCreateRequestDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        Livre livre = livreRepository.findById(dto.getLivreId())
                .orElseThrow(() -> new RuntimeException("Livre non trouvé"));

        Pret pret = new Pret();
        pret.setUser(user);
        pret.setLivre(livre);
        pret.setDatePret(dto.getDatePret());
        pret.setEstRetourne(dto.isEstRetourne());
        pret.setDateRetourEffectif(dto.getDateRetourEffectif());
        pret.setDateRetourPrevu(dto.getDateRetourPrevu());

        Pret saved = pretRepository.save(pret);
        return PretMapper.toDTO(saved);
    }

    public PretDTO modifierPret(Long id, PretCreateRequestDTO dto) {
        Pret pret = pretRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Réservation non trouvée"));

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        Livre livre = livreRepository.findById(dto.getLivreId())
                .orElseThrow(() -> new RuntimeException("Livre non trouvé"));

        pret.setUser(user);
        pret.setLivre(livre);
        pret.setDatePret(dto.getDatePret());
        pret.setEstRetourne(dto.isEstRetourne());
        pret.setDateRetourEffectif(dto.getDateRetourEffectif());
        pret.setDateRetourPrevu(dto.getDateRetourPrevu());

        Pret updated = pretRepository.save(pret);
        return PretMapper.toDTO(updated);
    }


    public void supprimerPret(Long id) {
        pretRepository.deleteById(id);
    }
}
