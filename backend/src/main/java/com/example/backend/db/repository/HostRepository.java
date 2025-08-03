package com.example.backend.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.db.model.Host;

public interface HostRepository extends JpaRepository<Host, Long>{
    
}
