package com.example.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
public class AdminController {
    
    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> loginAdmin(@Valid @RequestBody UserLoginDTO dto) {
        return ResponseEntity.ok(adminService.loginAdmin(dto));
    }

}
