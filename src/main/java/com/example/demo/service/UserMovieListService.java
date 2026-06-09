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
import com.example.demo.infra.Result;
import com.example.demo.repository.MovieRepository;
import com.example.demo.repository.UserMovieListRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserMovieListService {

    private final UserMovieListRepository userMovieListRepository;
    private final MovieRepository movieRepository;

    public List<MovieResponseDTO> getMoviesById(Long id) {
        return userMovieListRepository.findByUser_Id(id)
                .stream()
                .map(UserMovieList::getMovie)
                .map(MovieMapper::convertMovieToResponse).toList();
    }

    public Result<MovieResponseDTO> addMovieToList(User user, Long movieId) {
        UserMovieListKey userMovieListKey = new UserMovieListKey(user.getId(), movieId);
        if (userMovieListRepository.existsById(userMovieListKey)) return new Result.Conflict<>();

        Optional<Movie> movieOptional = movieRepository.findById(movieId);
        if (movieOptional.isEmpty()) return new Result.NotFound<>();

        userMovieListRepository.save(new UserMovieList(user, movieOptional.get(), userMovieListKey));
        Movie movie =  movieOptional.get();
        return new Result.Success<>(MovieMapper.convertMovieToResponse(movie));
    }

    public Result<Void> deleteById(Long userId, Long movieId) {
        UserMovieListKey key = new UserMovieListKey(userId, movieId);
        if (!userMovieListRepository.existsById(key)) return new Result.NotFound<>();

        userMovieListRepository.deleteById(key);
        return new Result.Success<>(null);
    }
}
/*Anotações de Estudo:
    addMovieToList()
        resolvi adotar um resultPattern, visando garantir o tratamento diferente dos 
        3 fluxos(filme adicionado, filme inexistente no BD e filme já presente),
        por não saber o quão excepcional seria o coportamento, dispensei exceções, dado seu custo computacional.
    deleteById()
        reutilizei a engenharia do ResultPattern do addMovieToList()
        no deleteById() para cobrir os 2 fluxos do método de forma limpa e padronizada.
*/
