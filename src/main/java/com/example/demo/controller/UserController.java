package com.example.demo.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.RegisterRequestDTO;
import com.example.demo.dto.UserResponseDTO;
import com.example.demo.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> findAll() {
        return ResponseEntity.ok().body(this.userService.findAll());
    }
    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<UserResponseDTO> user = userService.findById(id);
        if (!user.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of(
                            "error", "Not Found",
                            "message", "User with ID " + id + " Not Found",
                            "code", "USER_NOT_FOUND"
                    ));
        }
        return ResponseEntity.ok(user.get());
    }
    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody RegisterRequestDTO user) {
        Optional<UserResponseDTO> responseOptional = userService.createUser(user);
        if(responseOptional.isEmpty()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            Map.of(
                "error", "Bad Request",
                "message", "the email has already being used",
                "code", "EMAIL_DUPLICATION"
            )
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(responseOptional.get());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id){
        boolean userWasDeleted = userService.deleteUserById(id);
        if(!userWasDeleted)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "error", "Not Found",
                    "message", "User with ID " + id + " Not Found",
                    "code", "USER_NOT_FOUND"
            ));
        return ResponseEntity.noContent().build();
    }
}

