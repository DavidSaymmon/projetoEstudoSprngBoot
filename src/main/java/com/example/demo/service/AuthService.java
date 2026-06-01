package com.example.demo.service;

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

    public AuthResponseDTO register (RegisterRequestDTO request){
        if(userRepository.existsByEmail(request.email()))
            return null;
        User user = UserMapper.convertRequestToUser(request);
        user.setPassword(passwordEncoder.encode(request.password()));
        userRepository.save(user);
        return UserMapper.toAuthResponse(user);
    }
    public AuthResponseDTO login(LoginRequestDTO request){
        User user = userRepository.findByEmail(request.email()).orElse(null);                
        if(user == null || !passwordEncoder.matches(request.password(), user.getPassword()))
            return null;
        return UserMapper.toAuthResponse(user);
    }
}
