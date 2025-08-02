package com.example.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.db.repository.CottageRepository;

@RestController
public class TestController {
    
    @Autowired
    private CottageRepository cottageRepository;
    
    @GetMapping("/test")
    public String test() {
        return "Backend is working!";
    }

    @GetMapping("/testDb")
    public String testDb() {
        return "Backend DB connection is working! " +
                "Cottage row number is: " + cottageRepository.count();
    }

}
