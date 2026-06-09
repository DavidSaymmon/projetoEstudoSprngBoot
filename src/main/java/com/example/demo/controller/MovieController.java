package com.example.demo.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.MovieRequestDTO;
import com.example.demo.dto.MovieResponseDTO;
import com.example.demo.dto.MovieUpdateDTO;
import com.example.demo.service.MovieService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RequestMapping("/movie")
@RestController
public class MovieController {

    private final MovieService movieService;

    @PostMapping
    public ResponseEntity<MovieResponseDTO> createMovie(@Valid @RequestBody MovieRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.movieService.createMovie(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMovieById(@PathVariable Long id) {
        Optional<MovieResponseDTO> movie = this.movieService.getById(id);
        if (!movie.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "error", "Not_Found",
                    "message", "Movie with ID " + id + " Not Found",
                    "code", "MOVIE_NOT_FOUND"
            ));
        }
        return ResponseEntity.ok(movie.get());
    }

    @GetMapping
    public ResponseEntity<List<MovieResponseDTO>> getAllMovies() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateMovie(@PathVariable Long id, @Valid @RequestBody MovieUpdateDTO request) {
        Optional<MovieResponseDTO> movieOptional = movieService.updateMovie(id, request);
        if (movieOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "error", "Not_Found",
                    "message", "Movie with ID " + id + " Not Found",
                    "code", "MOVIE_NOT_FOUND"
            ));
        }
        return ResponseEntity.ok(movieOptional.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMovieById(@PathVariable Long id) {
        boolean movieWasDeleted = movieService.deleteById(id);
        if (!movieWasDeleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "error", "Not_Found",
                    "message", "Movie with ID " + id + " Not Found",
                    "code", "MOVIE_NOT_FOUND"
            ));
        }
        return ResponseEntity.noContent().build();
    }
}
