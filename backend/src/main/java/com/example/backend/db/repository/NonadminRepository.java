package com.example.backend.db.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.db.model.Nonadmin;
import com.example.backend.db.model.User;

import java.util.List;


public interface NonadminRepository extends JpaRepository<Nonadmin, Long>{
    boolean existsByEmail(String email);
    boolean existsByEmailAndIdNot(String email, Long id);
    Optional<Nonadmin> findByUsername(String username);
    List<Nonadmin> findByStatusNot(User.Status status);
}
