package com.example.webServices.services;

import com.example.webServices.dtos.UserCreateDTO;
import com.example.webServices.dtos.UserDTO;
import com.example.webServices.mappers.UserMapper;
import com.example.webServices.models.User;
import com.example.webServices.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(UserMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    }

    public UserDTO ajouterUser(UserCreateDTO userCreateDTO) {
        User user = new User();
        user.setNom(userCreateDTO.getNom());
        user.setEmail(userCreateDTO.getEmail());
        user.setRole(userCreateDTO.getRole());
        return UserMapper.toDTO(userRepository.save(user));
    }

    public UserDTO modifierUser(Long id, UserDTO userDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        User user = UserMapper.toEntity(userDTO);
        existingUser.setNom(user.getNom());
        existingUser.setEmail(user.getEmail());
        existingUser.setRole(user.getRole());
        return UserMapper.toDTO(userRepository.save(existingUser));
    }

    public void supprimerUser(Long id) {
        userRepository.deleteById(id);
    }
}
