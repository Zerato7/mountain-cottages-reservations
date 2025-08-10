package com.example.backend.service;

import com.example.backend.dto.RequestDTO.UserLoginDTO;
import com.example.backend.dto.ResponseDTO.MessageDTO;
import com.example.backend.dto.ResponseDTO.UserResponseDTO;

public interface AdminService {
    
    UserResponseDTO loginAdmin(UserLoginDTO dto);

    MessageDTO acceptNonadmin(Long id);

    MessageDTO rejectNonadmin(Long id);

    MessageDTO deactivateNonadmin(Long id);

    MessageDTO reactivateNonadmin(Long id);

}
