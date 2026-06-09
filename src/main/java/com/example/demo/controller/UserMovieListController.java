package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.MovieResponseDTO;
import com.example.demo.entity.User;
import com.example.demo.infra.Result;
import com.example.demo.service.UserMovieListService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/user/movie-list")
public class UserMovieListController {

    private final UserMovieListService userMovieListService;

    @GetMapping
    public ResponseEntity<List<MovieResponseDTO>> GetMoviesByUserId(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(userMovieListService.getMoviesById(user.getId()));
    }

    @PostMapping("/{movieId}")
    public ResponseEntity<?> addMovieToList(@AuthenticationPrincipal User user, @PathVariable Long movieId) {
        Result<MovieResponseDTO> addResult = userMovieListService.addMovieToList(user, movieId);

        return switch (addResult) {
            case Result.NotFound() ->
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                "error", "Not_Found",
                "message", "Movie with ID " + movieId + " Not Found",
                "code", "MOVIE_NOT_FOUND"));

            case Result.Conflict() ->
                ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                "error", "Conflict",
                "message", "Movie with ID " + movieId + " Is already in the user's list",
                "code", "MOVIE_ALREADY_ADDED"
                ));
            case Result.Success(MovieResponseDTO movie) ->
                ResponseEntity.ok(movie);
        };
    }

    @DeleteMapping("/{movieId}")
    public ResponseEntity<?> removeMovieFromList(@AuthenticationPrincipal User user, @PathVariable Long movieId) {
        Result<Void> result = userMovieListService.deleteById(user.getId(), movieId);
        if (result instanceof Result.NotFound()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "error", "Not_Found",
                    "message", "Movie with ID " + movieId + " Not Found in user's List",
                    "code", "MOVIE_NOT_IN_USER_LIST"
            ));
        }
        return ResponseEntity.noContent().build();
    }
}
