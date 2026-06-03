package com.example.demo.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.RegisterRequestDTO;
import com.example.demo.dto.UserResponseDTO;
import com.example.demo.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
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
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody RegisterRequestDTO user) {
        return ResponseEntity.status(HttpStatus.CREATED).
                body(this.userService.createUser(user));
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
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
    }
}
