package com.example.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dto.RequestDTO.UserLoginDTO;
import com.example.backend.dto.ResponseDTO.MessageDTO;
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
    
    @PutMapping("/accept/{id}")
    public ResponseEntity<MessageDTO> acceptNonadmin(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.acceptNonadmin(id));
    }

    @PutMapping("/reject/{id}")
    public ResponseEntity<MessageDTO> rejectNonadmin(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.rejectNonadmin(id));
    }

    @PutMapping("/deactivate/{id}")
    public ResponseEntity<MessageDTO> deactivateNonadmin(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.deactivateNonadmin(id));
    }

    @PutMapping("/reactivate/{id}")
    public ResponseEntity<MessageDTO> reactivateEntity(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.reactivateNonadmin(id));
    }

}
