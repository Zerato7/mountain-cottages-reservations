package com.example.backend.db.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.db.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
    public boolean existsByUsername(String username);
    public boolean existsByUsernameAndIdNot(String username, Long id);
    public Optional<User> findByUsername(String username);
}
