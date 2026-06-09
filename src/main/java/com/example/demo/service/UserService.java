package com.example.demo.service;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
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
    
    public Optional<UserResponseDTO> createUser (RegisterRequestDTO request){
        if(userRepository.existsByEmail(request.email())) 
            return Optional.empty(); 
        try{
            User user = UserMapper.convertRequestToUser(request);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            UserResponseDTO response = UserMapper.convertToResponseDTO(userRepository.save(user));
            
            return Optional.of(response);
        }
        catch(DataIntegrityViolationException e){
            return Optional.empty();
        }
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
    public boolean deleteUserById(Long id){
        if(!userRepository.existsById(id))
            return false;
        userRepository.deleteById(id);
        return true;
    }
}