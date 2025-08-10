package com.example.backend.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.backend.dto.RequestDTO.UserEditDTO;
import com.example.backend.dto.ResponseDTO.NonadminResponseDTO;

public interface NonadminService {

    List<NonadminResponseDTO> getAll();

    void editNonadmin(UserEditDTO dto, MultipartFile profilePicture);

}
