package com.example.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dto.UserLoginDTO;
import com.example.backend.dto.ResponseDTO.UserResponseDTO;
import com.example.backend.service.AdminService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {
    
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> loginAdmin(@Valid @RequestBody UserLoginDTO dto) {
        return ResponseEntity.ok(adminService.loginAdmin(dto));
    }

}
