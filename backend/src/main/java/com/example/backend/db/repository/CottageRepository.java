package com.example.backend.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.db.model.Cottage;

public interface CottageRepository extends JpaRepository<Cottage, Long> {
    List<Cottage> findByOwner_Id(Long ownerId);
    boolean existsByName(String name);
}
