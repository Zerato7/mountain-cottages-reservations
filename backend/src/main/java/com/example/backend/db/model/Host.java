package com.example.backend.db.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
public class Host extends Nonadmin {
    
    @OneToMany(mappedBy = "owner")
    private List<Cottage> cottages = new ArrayList<>();

}
