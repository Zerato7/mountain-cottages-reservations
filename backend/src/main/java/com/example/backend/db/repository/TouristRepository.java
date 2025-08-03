package com.example.backend.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.db.model.Tourist;

public interface TouristRepository extends JpaRepository<Tourist, Long>{
    
}
