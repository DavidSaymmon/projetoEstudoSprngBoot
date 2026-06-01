package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequestDTO(
        @NotBlank(message = "the name is mandatory")
        String name,
        @NotBlank(message = "the email is mandatory")
        @Email(message = "enter a valid email")
        String email,
        @NotBlank(message = "the password is mandatory")
        @Size(min = 6, message = "the password must have at least 6 characters")
        String password
        ) {}
