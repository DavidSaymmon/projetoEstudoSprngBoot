package com.example.demo.dto;

import java.time.LocalDateTime;

import com.example.demo.enums.Role;

public record UserResponseDTO(
    Long id,
    String name,
    String email,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    Role role
) {}


