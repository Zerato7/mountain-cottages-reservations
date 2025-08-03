package com.example.backend.service;

import com.example.backend.db.model.Nonadmin;
import com.example.backend.dto.UserLoginDTO;
import com.example.backend.dto.UserRegistrationDTO;
import com.example.backend.dto.ResponseDTO.NonadminResponseDTO;

public interface NonadminService {

    Nonadmin registerNonadmin(UserRegistrationDTO dto);

    NonadminResponseDTO loginNonadmin(UserLoginDTO dto);

}
