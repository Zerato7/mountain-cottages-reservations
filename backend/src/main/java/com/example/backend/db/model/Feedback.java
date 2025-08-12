package com.example.backend.db.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Feedback {
    
    @Id
    @OneToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @Column(length = 1000)
    private String comment;

    @Column(nullable = false)
    private Integer rating;

    // Getters and Setters
    
    public Reservation getReservation() {
        return reservation;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

}
