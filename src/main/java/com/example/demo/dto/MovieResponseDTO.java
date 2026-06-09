package com.example.demo.dto;

import java.time.LocalDateTime;

import com.example.demo.enums.Category;

public record MovieResponseDTO(
    Long id,
    Category category,
    String name,
    String language,
    LocalDateTime releaseDate,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    Integer duration
) {

}
