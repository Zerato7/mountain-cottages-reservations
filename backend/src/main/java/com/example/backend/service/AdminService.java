package com.example.backend.service;

import com.example.backend.dto.UserLoginDTO;
import com.example.backend.dto.ResponseDTO.UserResponseDTO;

public interface AdminService {
    
    UserResponseDTO loginAdmin(UserLoginDTO dto);

}
