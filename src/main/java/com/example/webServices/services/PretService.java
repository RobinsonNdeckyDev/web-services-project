package com.example.webServices.services;

import com.example.webServices.dtos.PretDTO;
import com.example.webServices.mappers.PretMapper;
import com.example.webServices.models.Pret;
import com.example.webServices.repository.PretRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PretService {

    private final PretRepository pretRepository;

    public PretService(PretRepository pretRepository) {
        this.pretRepository = pretRepository;
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

    public PretDTO ajouterPret(PretDTO pretDTO) {
        Pret pret = PretMapper.toEntity(pretDTO);
        return PretMapper.toDTO(pretRepository.save(pret));
    }

    public PretDTO modifierPret(Long id, PretDTO pretDTO) {
        Pret existingPret = pretRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prêt non trouvé"));
        Pret pret = PretMapper.toEntity(pretDTO);
        existingPret.setDatePret(pret.getDatePret());
        existingPret.setDateRetourPrevu(pret.getDateRetourPrevu());
        existingPret.setDateRetourEffectif(pret.getDateRetourEffectif());
        existingPret.setEstRetourne(pret.isEstRetourne());
        return PretMapper.toDTO(pretRepository.save(existingPret));
    }

    public void supprimerPret(Long id) {
        pretRepository.deleteById(id);
    }
}
