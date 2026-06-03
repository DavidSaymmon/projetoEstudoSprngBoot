package com.example.demo.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.MovieRequestDTO;
import com.example.demo.dto.MovieResponseDTO;
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
        Optional<MovieResponseDTO> movieOPT = this.movieService.getById(id);
        if (movieOPT.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "error", "Not_Found",
                    "message", "Movie with ID " + id + " Not Found",
                    "code", "MOVIE_NOT_FOUND"
            ));
        }
        return ResponseEntity.ok(movieOPT.get());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMovieById(@PathVariable Long id){
        boolean movieWasDeleted = movieService.deleteById(id);
        if(!movieWasDeleted)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                "error", "Not_Found",
                "message", "Movie with ID " + id + " Not Found",
                "code", "MOVIE_NOT_FOUND"
        ));
      return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
    }
}
