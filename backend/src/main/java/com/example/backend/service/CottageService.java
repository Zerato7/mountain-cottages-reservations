package com.example.backend.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.backend.dto.RequestDTO.CottageInsertDTO;
import com.example.backend.dto.ResponseDTO.CottageResponseDTO;

public interface CottageService {
    
    List<CottageResponseDTO> getAll();

    List<CottageResponseDTO> getMy(Long id);

    CottageResponseDTO createCottage(CottageInsertDTO dto, List<MultipartFile> cottagePhotosFile);

    void deleteCottage(Long id);

}
