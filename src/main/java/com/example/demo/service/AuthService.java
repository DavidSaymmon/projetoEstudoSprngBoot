package com.example.demo.service;

import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.AuthResponseDTO;
import com.example.demo.dto.LoginRequestDTO;
import com.example.demo.dto.RegisterRequestDTO;
import com.example.demo.entity.User;
import com.example.demo.infra.UserMapper;
import com.example.demo.repository.UserRepository;

import lombok.AllArgsConstructor;
@AllArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; 
    private final JwtService jwtService;
    public Optional<AuthResponseDTO> register (RegisterRequestDTO request){
        if(userRepository.existsByEmail(request.email()))
            return Optional.empty();
        try{   
            User user = UserMapper.convertRequestToUser(request);
            user.setPassword(passwordEncoder.encode(request.password()));
            userRepository.save(user);
            return Optional.of(UserMapper.toAuthResponse(user, jwtService.generateToken(user)));
        }
        catch(DataIntegrityViolationException e){
            return Optional.empty();
        }
    }
    public Optional<AuthResponseDTO> login(LoginRequestDTO request){
        User user = userRepository.findByEmail(request.email()).orElse(null);                
        if(user == null || !passwordEncoder.matches(request.password(), user.getPassword()))
            return Optional.empty();
        return Optional.of(UserMapper.toAuthResponse(user, jwtService.generateToken(user)));
    }
}
/*
Anotações de Estudo:
    usei um retorno de Optional vazio para lidar com casos normais de tentativa de uso de e-mail duplicado
    e um try catch para lidar com condições de corrida, ex:
    múltiplos cliques num botão de registro num front mal formulado.
 */