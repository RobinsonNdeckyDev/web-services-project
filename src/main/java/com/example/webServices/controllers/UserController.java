package com.example.webServices.controllers;

import com.example.webServices.dtos.UserCreateDTO;
import com.example.webServices.dtos.UserDTO;
import com.example.webServices.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping
    public ResponseEntity<UserDTO> ajouterUser(@RequestBody UserCreateDTO userCreateDTO) {
        return ResponseEntity.status(201).body(userService.ajouterUser(userCreateDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> modifierUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.modifierUser(id, userDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerUser(@PathVariable Long id) {
        userService.supprimerUser(id);
        return ResponseEntity.noContent().build();
    }
}
