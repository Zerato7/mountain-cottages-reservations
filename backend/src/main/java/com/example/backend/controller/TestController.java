package com.example.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.db.repository.CottageRepository;
import com.example.backend.util.EncryptionUtil;

@RestController
@RequestMapping("/test")
public class TestController {
    
    @Autowired
    private EncryptionUtil encryptionUtil;
    @Autowired
    private CottageRepository cottageRepository;
    
    @GetMapping("/")
    public String test() {
        return "Backend is working!";
    }

    @GetMapping("/db")
    public String testDb() {
        return "Backend DB connection is working! " +
                "Cottage row number is: " + cottageRepository.count();
    }

    @PostMapping("/encrypt")
    public String testEncrypt(@RequestBody String text) {
        return encryptionUtil.decrypt(text);
    }

}
