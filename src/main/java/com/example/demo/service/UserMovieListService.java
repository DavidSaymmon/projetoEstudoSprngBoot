package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.dto.MovieResponseDTO;
import com.example.demo.entity.Movie;
import com.example.demo.entity.User;
import com.example.demo.entity.UserMovieList;
import com.example.demo.entity.UserMovieListKey;
import com.example.demo.infra.MovieMapper;
import com.example.demo.repository.MovieRepository;
import com.example.demo.repository.UserMovieListRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserMovieListService {
    private final UserMovieListRepository userMovieListRepository;
    private final MovieRepository movieRepository;
    public List<MovieResponseDTO> getMoviesById(Long id){
        return userMovieListRepository.findByUser_Id(id)
                .stream()
                .map(UserMovieList::getMovie)
                .map(MovieMapper::convertMovieToResponse).toList();
    }
    public Optional<Movie> addMovieToList(User user, Long movieId){
        Optional<Movie> movieOptional = movieRepository.findById(movieId);
        if(movieOptional.isPresent()) {
            UserMovieListKey userMovieListKey = new UserMovieListKey(user.getId(), movieId);
            userMovieListRepository.save(new UserMovieList(user, movieOptional.get(), userMovieListKey));
        }
        return movieOptional;
    }
    public boolean deleteById(Long userId, Long movieId){
        UserMovieListKey key = new UserMovieListKey(userId, movieId);
        if(!userMovieListRepository.existsById(key))
            return false;
        userMovieListRepository.deleteById(key);
        return true;
    }
}
