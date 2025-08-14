package com.example.backend.service;

import org.springframework.web.multipart.MultipartFile;

import com.example.backend.dto.RequestDTO.PasswordChangeDTO;
import com.example.backend.dto.RequestDTO.UserLoginDTO;
import com.example.backend.dto.RequestDTO.UserRegistrationDTO;
import com.example.backend.dto.ResponseDTO.NonadminResponseDTO;

public interface AuthService {

    NonadminResponseDTO registerNonadmin(UserRegistrationDTO dto, MultipartFile profilePicture);

    NonadminResponseDTO loginNonadmin(UserLoginDTO dto);

    void changePassword(PasswordChangeDTO dto);

}
