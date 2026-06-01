package com.example.demo.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.UserMovieList;
import com.example.demo.entity.UserMovieListKey;

public interface UserMovieListRepository extends JpaRepository<UserMovieList,  UserMovieListKey>{

}