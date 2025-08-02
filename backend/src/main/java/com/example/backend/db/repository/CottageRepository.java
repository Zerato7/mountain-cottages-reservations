package com.example.backend.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.db.model.Cottage;

public interface CottageRepository extends JpaRepository<Cottage, Integer> {
    
}
