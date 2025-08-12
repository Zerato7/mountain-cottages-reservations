package com.example.backend.db.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
public class Tourist extends Nonadmin {
    
    @OneToMany(mappedBy = "tourist")
    private List<Reservation> reservations = new ArrayList<>();

}
