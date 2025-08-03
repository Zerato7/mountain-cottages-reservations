package com.example.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dto.UserLoginDTO;
import com.example.backend.dto.UserRegistrationDTO;
import com.example.backend.dto.ResponseDTO.NonadminResponseDTO;
import com.example.backend.service.NonadminService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/nonadmin")
public class NonadminController {

    @Autowired
    private NonadminService nonadminService;

    @PostMapping("/register")
    public ResponseEntity<?> registerNonadmin(@Valid @RequestBody UserRegistrationDTO dto) {
        nonadminService.registerNonadmin(dto);
        return ResponseEntity.ok("Zahtev za registracijom uspešno poslat.");
    }

    @PostMapping("/login")
    public ResponseEntity<NonadminResponseDTO> loginNonadmin(@Valid @RequestBody UserLoginDTO dto) {
        return ResponseEntity.ok(nonadminService.loginNonadmin(dto));
    }

}
