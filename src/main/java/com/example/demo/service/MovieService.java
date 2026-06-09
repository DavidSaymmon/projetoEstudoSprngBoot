package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.dto.MovieRequestDTO;
import com.example.demo.dto.MovieResponseDTO;
import com.example.demo.dto.MovieUpdateDTO;
import com.example.demo.entity.Movie;
import com.example.demo.infra.MovieMapper;
import com.example.demo.repository.MovieRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieResponseDTO createMovie(MovieRequestDTO request) {
        return MovieMapper.convertMovieToResponse(
                movieRepository.save(MovieMapper.convertRequestToMovie(request)
                ));
    }

    public Optional<MovieResponseDTO> getById(Long Id) {
        return movieRepository.findById(Id)
                .map(movie -> MovieMapper.convertMovieToResponse(movie));
    }

    public List<MovieResponseDTO> getAllMovies() {
        return movieRepository.findAll()
                .stream()
                .map(movie -> MovieMapper.convertMovieToResponse(movie)).toList();
    }

    public Optional<MovieResponseDTO> updateMovie(Long id, MovieUpdateDTO request) {
        Optional<Movie> movieOPT = movieRepository.findById(id);
        if (movieOPT.isEmpty()) 
            return Optional.empty();

        Movie movie = movieOPT.get();
        if (request.category() != null) movie.setCategory(request.category());
        if (request.name() != null && !request.name().isBlank()) movie.setName(request.name());
        if (request.language() != null && !request.language().isBlank()) movie.setLanguage(request.language());
        if (request.duration() != null) movie.setDuration(request.duration());
        if (request.releaseDate() != null) movie.setReleaseDate(request.releaseDate());
        
        return Optional.of(MovieMapper.convertMovieToResponse(movieRepository.save(movie)));
    }

    public boolean deleteById(Long id) {
        if (!movieRepository.existsById(id)) {
            return false;
        }
        movieRepository.deleteById(id);
        return true;
    }
}
