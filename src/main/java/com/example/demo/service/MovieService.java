package com.example.demo.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.dto.MovieRequestDTO;
import com.example.demo.dto.MovieResponseDTO;
import com.example.demo.infra.MovieMapper;
import com.example.demo.repository.MovieRepository;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MovieService {
private final MovieRepository movieRepository;
    public MovieResponseDTO createMovie (@Valid MovieRequestDTO request){
        return MovieMapper.convertMovieToResponse(
            movieRepository.save(MovieMapper.convertRequestToMovie(request)
        ));
    }
    public Optional<MovieResponseDTO> getById (Long Id){
        return movieRepository.findById(Id)
        .map(movie->MovieMapper.convertMovieToResponse(movie));
    }
    public boolean deleteById(Long id){
        if(!movieRepository.existsById(id)) return false;
        movieRepository.deleteById(id);
        return true;
    }
}
