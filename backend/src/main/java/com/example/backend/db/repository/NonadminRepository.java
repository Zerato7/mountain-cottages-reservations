package com.example.backend.db.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.db.model.Nonadmin;

public interface NonadminRepository extends JpaRepository<Nonadmin, Long>{
    public boolean existsByEmail(String email);
    public Optional<Nonadmin> findByUsername(String username);
}
