package com.example.demo.dto;

import java.time.LocalDateTime;

import com.example.demo.enums.Category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record MovieUpdateDTO(
        Category category,
        @Size(min=1)
        String name,
        @Size(min = 2)
        String language,
        LocalDateTime releaseDate,
        @Positive
        Integer duration
        ) {}
