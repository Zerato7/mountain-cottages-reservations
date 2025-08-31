package com.example.backend.dto.ResponseDTO;

import java.time.OffsetDateTime;

public class FeedbackResponseDTO {
    
    private Long reservationId;
    private Integer rating;
    private String comment;
    private OffsetDateTime dateTimeCreation;

    // Getters and Setters

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }

    public OffsetDateTime getDateTimeCreation() {
        return dateTimeCreation;
    }

    public void setDateTimeCreation(OffsetDateTime dateTimeCreation) {
        this.dateTimeCreation = dateTimeCreation;
    }
    
}
