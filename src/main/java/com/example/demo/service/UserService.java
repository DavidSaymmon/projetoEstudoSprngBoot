package com.example.demo.service;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.RegisterRequestDTO;
import com.example.demo.dto.UserResponseDTO;
import com.example.demo.entity.User;
import com.example.demo.infra.UserMapper;
import com.example.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    public UserResponseDTO createUser (RegisterRequestDTO request){
        User user = UserMapper.convertRequestToUser(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return UserMapper.convertToResponseDTO(userRepository.save(user));
    }
    public Optional<UserResponseDTO> findById(Long id) {
        return userRepository.findById(id)
            .map(user->UserMapper.convertToResponseDTO(user));
    }
    public List<UserResponseDTO> findAll(){
        return userRepository.findAll()
        .stream()
        .map(user -> UserMapper.convertToResponseDTO(user))
        .toList();
    }
}