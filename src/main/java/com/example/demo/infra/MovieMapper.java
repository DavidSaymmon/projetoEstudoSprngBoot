package com.example.demo.infra;

import com.example.demo.dto.MovieRequestDTO;
import com.example.demo.dto.MovieResponseDTO;
import com.example.demo.entity.Movie;

public class MovieMapper {

    public static Movie convertRequestToMovie(MovieRequestDTO request) {
        return new Movie(
                request.category(),
                request.name(),
                request.language(),
                request.releaseDate(),
                request.duration()
        );
    }
    public static MovieResponseDTO convertMovieToResponse(Movie movie){
        return new MovieResponseDTO(
            movie.getId(),
            movie.getCategory(),
            movie.getName(),
            movie.getLanguage(),
            movie.getReleaseDate(),
            movie.getCreatedAt(),
            movie.getUpdatedAt(),
            movie.getDuration()
        );
    }
}
