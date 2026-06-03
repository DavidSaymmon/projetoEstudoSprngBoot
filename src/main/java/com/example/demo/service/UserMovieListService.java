package com.example.demo.service;

import com.example.demo.entity.Movie;
import com.example.demo.entity.User;
import com.example.demo.entity.UserMovieList;
import com.example.demo.entity.UserMovieListKey;
import com.example.demo.repository.MovieRepository;
import com.example.demo.repository.UserMovieListRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserMovieListService {
    private final UserMovieListRepository userMovieListRepository;
    private final MovieRepository movieRepository;
    public List<Movie> getMoviesById(Long id){
        return userMovieListRepository.findByUser_Id(id)
                .stream()
                .map(UserMovieList::getMovie).toList();
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
