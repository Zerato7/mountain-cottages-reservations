package com.example.backend.service.impl;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.backend.db.model.User;
import com.example.backend.db.repository.UserRepository;
import com.example.backend.dto.RequestDTO.UserLoginDTO;
import com.example.backend.dto.ResponseDTO.MessageDTO;
import com.example.backend.dto.ResponseDTO.UserResponseDTO;
import com.example.backend.exception.AuthException;
import com.example.backend.exception.BadRequestException;
import com.example.backend.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    public AdminServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public UserResponseDTO loginAdmin(UserLoginDTO dto) {
        User user = userRepository.findByUsername(dto.getUsername()).orElseThrow(() ->
            new AuthException("Невалидни корисничко име или лозинка.")
        );
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new AuthException("Невалидни корисничко име или лозинка.");
        }
        if (!user.isAdmin()) {
            throw new AuthException("Корисник није администратор.");
        }
        if (user.getStatus() != User.Status.ACCEPTED) {
            throw new BadRequestException("Корисник је статуса: " + user.getStatus());
        }
        UserResponseDTO response = new UserResponseDTO();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setUserType(user.getUserType());
        response.setStatus(user.getStatus());
        return response;
    }

    @Override
    public MessageDTO acceptNonadmin(Long id) {
        return changeStatusTo(id, User.Status.PENDING, User.Status.ACCEPTED, 
            "Регистрација прихваћена.", "Прихватање захтева за регистрацијом није успело.");
    }

    @Override
    public MessageDTO rejectNonadmin(Long id) {
        return changeStatusTo(id, User.Status.PENDING, User.Status.REJECTED, 
        "Регистрација одбијена.", "Одбијање захтева за регистрацијом није успело.");
    }

    @Override
    public MessageDTO deactivateNonadmin(Long id) {
        return changeStatusTo(id, User.Status.ACCEPTED, User.Status.DEACTIVATED, 
            "Корисник деактивиран.", "Деактивација корисника није успела.");
    }

    @Override
    public MessageDTO reactivateNonadmin(Long id) {
        return changeStatusTo(id, User.Status.DEACTIVATED, User.Status.ACCEPTED, 
            "Корисник поново активиран.", "Поновна активација корисника није успела.");
    }

    private MessageDTO changeStatusTo(Long id, User.Status statusFrom, User.Status statusTo, String successMessage, String errorMessage) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent() && user.get().getStatus() == statusFrom) {
            user.get().setStatus(statusTo);
            userRepository.save(user.get());
            return new MessageDTO(successMessage);
        } else {
            throw new BadRequestException(errorMessage);
        }
    }
    
}
