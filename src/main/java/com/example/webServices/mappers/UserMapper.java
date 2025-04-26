package com.example.webServices.mappers;

import com.example.webServices.dtos.UserDTO;
import com.example.webServices.models.User;

public interface UserMapper {
    public static UserDTO toDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getNom(),
                user.getEmail(),
                user.getRole()
        );
    }

    public static User toEntity(UserDTO dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setNom(dto.getNom());
        user.setEmail(dto.getEmail());
        user.setRole(dto.getRole());
        return user;
    }
}
