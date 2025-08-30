package com.example.backend.db.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.db.model.Cottage;

public interface CottageRepository extends JpaRepository<Cottage, Long> {
    List<Cottage> findByOwner_Id(Long ownerId);
    Optional<Cottage> findByName(String name);
    boolean existsByName(String name);
}
