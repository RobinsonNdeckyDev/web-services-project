package com.example.webServices.controllers;

import com.example.webServices.dtos.ReservationCreateRequestTDO;
import com.example.webServices.dtos.ReservationDTO;
import com.example.webServices.services.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<List<ReservationDTO>> getAllReservations() {
        return ResponseEntity.ok(reservationService.getAllReservations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationDTO> getReservationById(@PathVariable Long id) {
        return ResponseEntity.ok(reservationService.getReservationById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReservationDTO>> getReservationsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(reservationService.getReservationsByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<ReservationDTO> ajouterReservation(@RequestBody ReservationCreateRequestTDO reservationDTO) {
        return ResponseEntity.status(201).body(reservationService.ajouterReservation(reservationDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservationDTO> modifierReservation(@PathVariable Long id, @RequestBody ReservationCreateRequestTDO reservationDTO) {
        return ResponseEntity.ok(reservationService.modifierReservation(id, reservationDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerReservation(@PathVariable Long id) {
        reservationService.supprimerReservation(id);
        return ResponseEntity.noContent().build();
    }
}
