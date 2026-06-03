package com.example.demo.entity;
import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class UserMovieListKey implements Serializable{
   private Long userId;
   private Long movieId;
   @Override
   public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserMovieListKey that = (UserMovieListKey) o;
        return Objects.equals(userId, that.userId) && 
               Objects.equals(movieId, that.movieId);
    }

    public UserMovieListKey() {
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, movieId);
    }

    public UserMovieListKey(Long userId, Long movieId) {
        this.userId = userId;
        this.movieId = movieId;
    }
}