package com.example.backend.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.backend.dto.RequestDTO.CottageInsertDTO;
import com.example.backend.dto.ResponseDTO.CottageResponseDTO;
import com.example.backend.dto.ResponseDTO.MessageDTO;
import com.example.backend.service.CottageService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/cottage")
@CrossOrigin(origins = "http://localhost:4200")
public class CottageController {
    
    private final CottageService cottageService;

    public CottageController(CottageService cottageService) {
        this.cottageService = cottageService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<CottageResponseDTO> > getAllCottages() {
        return ResponseEntity.ok(cottageService.getAll());
    }

    @GetMapping("/getMy/{id}")
    public ResponseEntity<List<CottageResponseDTO> > getMyCottages(@PathVariable Long id) {
        return ResponseEntity.ok(cottageService.getMy(id));
    }

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CottageResponseDTO> createCottage(
        @Valid @RequestPart("cottage") CottageInsertDTO dto,
        @RequestPart(value = "images", required = false) List<MultipartFile> images 
    ) {
        return ResponseEntity.ok(cottageService.createCottage(dto, images));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<MessageDTO> deleteCottage(@PathVariable Long id) {
        cottageService.deleteCottage(id);
        return ResponseEntity.ok(new MessageDTO("Викендица је успешно обрисана."));
    }

}
