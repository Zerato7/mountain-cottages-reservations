package com.example.backend.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.backend.dto.RequestDTO.UserEditDTO;
import com.example.backend.dto.ResponseDTO.MessageDTO;
import com.example.backend.dto.ResponseDTO.NonadminResponseDTO;
import com.example.backend.service.NonadminService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/nonadmin")
@CrossOrigin(origins = "http://localhost:4200")
public class NonadminController {
    
    private final NonadminService nonadminService;

    public NonadminController(NonadminService nonadminService) {
        this.nonadminService = nonadminService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<NonadminResponseDTO> > getNonadmins() {
        return ResponseEntity.ok(nonadminService.getAll());
    }

    @PutMapping(value = "/edit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MessageDTO> editNonadmin(
        @Valid @RequestPart("user") UserEditDTO dto,
        @RequestPart(value = "profilePicture", required = false) MultipartFile profilePicture
    ) {
        nonadminService.editNonadmin(dto, profilePicture);
        return ResponseEntity.ok(new MessageDTO("Подаци о неадмину успешно измењени."));
    }

}
