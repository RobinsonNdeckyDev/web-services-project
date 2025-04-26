package com.example.webServices.controllers;

import com.example.webServices.dtos.LivreDTO;
import com.example.webServices.services.LivreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/livres")
public class LivreController {

    private final LivreService livreService;

    public LivreController(LivreService livreService) {
        this.livreService = livreService;
    }

    @GetMapping
    public ResponseEntity<List<LivreDTO>> getAllLivres() {
        return ResponseEntity.ok(livreService.getAllLivres());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivreDTO> getLivreById(@PathVariable Long id) {
        return ResponseEntity.ok(livreService.getLivreById(id));
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<LivreDTO>> getLivresDisponibles() {
        return ResponseEntity.ok(livreService.getLivresDisponibles());
    }

    @PostMapping
    public ResponseEntity<LivreDTO> ajouterLivre(@RequestBody LivreDTO livreDTO) {
        return ResponseEntity.status(201).body(livreService.ajouterLivre(livreDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LivreDTO> modifierLivre(@PathVariable Long id, @RequestBody LivreDTO livreDTO) {
        return ResponseEntity.ok(livreService.modifierLivre(id, livreDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerLivre(@PathVariable Long id) {
        livreService.supprimerLivre(id);
        return ResponseEntity.noContent().build();
    }
}
