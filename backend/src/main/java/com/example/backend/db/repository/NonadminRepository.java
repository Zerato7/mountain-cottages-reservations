package com.example.backend.db.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.db.model.Nonadmin;
import com.example.backend.db.model.User;

import java.util.List;


public interface NonadminRepository extends JpaRepository<Nonadmin, Long>{
    public boolean existsByEmail(String email);
    public boolean existsByEmailAndIdNot(String email, Long id);
    public Optional<Nonadmin> findByUsername(String username);
    public List<Nonadmin> findByStatusNot(User.Status status);
}
