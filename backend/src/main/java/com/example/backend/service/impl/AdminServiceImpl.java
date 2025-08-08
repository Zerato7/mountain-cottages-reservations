package com.example.backend.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.backend.db.model.User;
import com.example.backend.db.repository.UserRepository;
import com.example.backend.dto.UserLoginDTO;
import com.example.backend.dto.ResponseDTO.UserResponseDTO;
import com.example.backend.exception.AuthException;
import com.example.backend.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponseDTO loginAdmin(UserLoginDTO dto) {
        User user = userRepository.findByUsername(dto.getUsername()).orElseThrow(() ->
            new AuthException("Nevalidni korisničko ime ili lozinka.")
        );
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new AuthException("Nevalidni korisničko ime ili lozinka.");
        }
        if (!user.isAdmin()) {
            throw new AuthException("Korisnik nije administrator.");
        }
        UserResponseDTO response = new UserResponseDTO();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setUserType(user.getUserType());
        return response;
    }
    
}
