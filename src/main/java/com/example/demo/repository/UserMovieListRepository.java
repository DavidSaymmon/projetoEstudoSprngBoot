package com.example.demo.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.UserMovieList;
import com.example.demo.entity.UserMovieListKey;

import java.util.List;

public interface UserMovieListRepository extends JpaRepository<UserMovieList,  UserMovieListKey>{
    List<UserMovieList> findByUser_Id(Long id);
}