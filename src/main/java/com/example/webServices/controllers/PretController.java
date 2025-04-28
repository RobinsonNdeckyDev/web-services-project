package com.example.webServices.controllers;

import com.example.webServices.dtos.PretCreateRequestDTO;
import com.example.webServices.dtos.PretDTO;
import com.example.webServices.dtos.ReservationCreateRequestTDO;
import com.example.webServices.dtos.ReservationDTO;
import com.example.webServices.services.PretService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prets")
public class PretController {

    private final PretService pretService;

    public PretController(PretService pretService) {
        this.pretService = pretService;
    }

    @GetMapping
    public ResponseEntity<List<PretDTO>> getAllPrets() {
        return ResponseEntity.ok(pretService.getAllPrets());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PretDTO> getPretById(@PathVariable Long id) {
        return ResponseEntity.ok(pretService.getPretById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PretDTO>> getPretsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(pretService.getPretsByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<PretDTO> ajouterPret(@RequestBody PretCreateRequestDTO pretCreateRequestDTO) {
        return ResponseEntity.status(201).body(pretService.ajouterPret(pretCreateRequestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PretDTO> modifierReservation(@PathVariable Long id, @RequestBody PretCreateRequestDTO pretCreateRequestDTO) {
        return ResponseEntity.ok(pretService.modifierPret(id, pretCreateRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerPret(@PathVariable Long id) {
        pretService.supprimerPret(id);
        return ResponseEntity.noContent().build();
    }
}
