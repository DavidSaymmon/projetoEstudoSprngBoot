package com.example.demo.dto;

import java.time.Duration;
import java.time.LocalDateTime;

import com.example.demo.enums.Category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MovieRequestDTO (
@NotNull(message="category is a mandatory field")
Category category,
@NotBlank(message="name is a mandatory field")
String name,
@NotBlank(message="language is a mandatory field")
@Size(min=2)
String language,
@NotNull(message="releaseDate is a mandatory field")
LocalDateTime releaseDate,
@NotNull(message="duration is a mandatory field")
Duration duration) {}

