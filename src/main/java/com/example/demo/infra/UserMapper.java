package com.example.demo.infra;

import com.example.demo.dto.AuthResponseDTO;
import com.example.demo.dto.RegisterRequestDTO;
import com.example.demo.dto.UserResponseDTO;
import com.example.demo.entity.User;
import com.example.demo.service.JwtService;

public class UserMapper {
private static final JwtService jwtService = new JwtService();
    
    public static UserResponseDTO convertToResponseDTO(User user) {
        
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getRole()
        );
    }

    public static User convertRequestToUser(RegisterRequestDTO request) {
        return new User(
                request.name(),
                request.email(),
                request.password()
        );
    }
    public static AuthResponseDTO toAuthResponse(User user) {
        return new AuthResponseDTO(
                jwtService.generateToken(user),
                convertToResponseDTO(user)
        );
    }
}
