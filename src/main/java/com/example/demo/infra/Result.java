package com.example.demo.infra;

public sealed interface Result<T> {
    record Success<T>(T obj) implements Result<T> {

    } 
    record NotFound<T>() implements Result<T> {
    }
    record Conflict<T>() implements Result<T> {
    }
}
