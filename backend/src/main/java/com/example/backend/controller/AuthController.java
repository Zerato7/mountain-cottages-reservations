package com.example.backend.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.backend.dto.RequestDTO.PasswordChangeDTO;
import com.example.backend.dto.RequestDTO.UserLoginDTO;
import com.example.backend.dto.RequestDTO.UserRegistrationDTO;
import com.example.backend.dto.ResponseDTO.MessageDTO;
import com.example.backend.dto.ResponseDTO.NonadminResponseDTO;
import com.example.backend.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MessageDTO> registerNonadmin(
        @Valid @RequestPart("user") UserRegistrationDTO dto,
        @RequestPart(value = "profilePicture", required = false) MultipartFile profilePicture) {
        authService.registerNonadmin(dto, profilePicture);
        return ResponseEntity.ok(new MessageDTO("Zahtev za registracijom uspešno poslat."));
    }

    @PostMapping("/login")
    public ResponseEntity<NonadminResponseDTO> loginNonadmin(@Valid @RequestBody UserLoginDTO dto) {
        return ResponseEntity.ok(authService.loginNonadmin(dto));
    }

    @PutMapping("/change-password")
    public ResponseEntity<MessageDTO> changePassword(@Valid @RequestBody PasswordChangeDTO dto) {
        authService.changePassword(dto);
        return ResponseEntity.ok(new MessageDTO("Успешно промењена лозинка."));
    }

}
