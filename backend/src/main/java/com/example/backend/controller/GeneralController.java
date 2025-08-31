package com.example.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dto.ResponseDTO.GeneralInfoDTO;
import com.example.backend.service.GeneralService;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "http://localhost:4200")
public class GeneralController {
    
    private final GeneralService generalService;

    GeneralController(
        GeneralService generalSevice
    ) {
        this.generalService = generalSevice;
    }

    @GetMapping("/getInfo")
    public ResponseEntity<GeneralInfoDTO> getInfo() {
        return ResponseEntity.ok(generalService.getInfo());
    }

}
