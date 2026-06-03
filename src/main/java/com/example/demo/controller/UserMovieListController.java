package com.example.demo.controller;

import com.example.demo.entity.Movie;
import com.example.demo.entity.User;
import com.example.demo.entity.UserMovieList;
import com.example.demo.entity.UserMovieListKey;
import com.example.demo.repository.MovieRepository;
import com.example.demo.repository.UserMovieListRepository;
import com.example.demo.service.UserMovieListService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/user/movie-list")
public class UserMovieListController {
    private final UserMovieListService userMovieListService;
    private final UserMovieListRepository userMovieListRepository;
    private final MovieRepository movieRepository;
    @GetMapping
    public ResponseEntity<List<Movie>> GetMoviesByUserId(@AuthenticationPrincipal User user){
        return ResponseEntity.ok(userMovieListService.getMoviesById(user.getId()));
    }
    @PostMapping("/{movieId}")
    public ResponseEntity<?> addMovieToList(@AuthenticationPrincipal User user, @PathVariable Long movieId){
        Optional<Movie> movieOptional = userMovieListService.addMovieToList(user, movieId);
        if(movieOptional.isEmpty())
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                "error", "Not_Found",
                "message", "Movie with ID " + movieId + " Not Found",
                "code", "MOVIE_NOT_FOUND"
        ));//To Do, adicionar status mais genérico que englobe Unique Constraitn
        return ResponseEntity.status(HttpStatus.CREATED).body(movieOptional.get());
    }
    @DeleteMapping("/{movieId}")
    public ResponseEntity<?> removeMovieFromList(@AuthenticationPrincipal User user, @PathVariable Long movieId){
        boolean movieWasRemoved = userMovieListService.deleteById(user.getId(), movieId);
        if(!movieWasRemoved)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "error", "Not_Found",
                    "message", "Movie with ID " + movieId + " Not Found",
                    "code", "MOVIE_NOT_FOUND"
            ));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
    }
}
