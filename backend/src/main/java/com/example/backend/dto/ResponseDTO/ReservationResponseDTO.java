package com.example.backend.dto.ResponseDTO;

import java.time.LocalDateTime;

import com.example.backend.db.model.Reservation;

public class ReservationResponseDTO {
    
    private String username;
    private String cottageName;

    private LocalDateTime datetimeStart;
    private LocalDateTime datetimeEnd;
    private Integer adultsNumber;
    private Integer childrenNumber;
    private Double cost;
    private String specialDemands;
    private Reservation.Status status;
    private String rejectionComment;

    private FeedbackResponseDTO feedback;

    // Getters and Setters

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCottageName() {
        return cottageName;
    }

    public void setCottageName(String cottageName) {
        this.cottageName = cottageName;
    }

    public LocalDateTime getDatetimeStart() {
        return datetimeStart;
    }

    public void setDatetimeStart(LocalDateTime datetimeStart) {
        this.datetimeStart = datetimeStart;
    }

    public LocalDateTime getDatetimeEnd() {
        return datetimeEnd;
    }

    public void setDatetimeEnd(LocalDateTime datetimeEnd) {
        this.datetimeEnd = datetimeEnd;
    }

    public Integer getAdultsNumber() {
        return adultsNumber;
    }

    public void setAdultsNumber(Integer adultsNumber) {
        this.adultsNumber = adultsNumber;
    }

    public Integer getChildrenNumber() {
        return childrenNumber;
    }

    public void setChildrenNumber(Integer childrenNumber) {
        this.childrenNumber = childrenNumber;
    }

    public Double getCost() {
        return cost;
    }
    
    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String getSpecialDemands() {
        return specialDemands;
    }

    public void setSpecialDemands(String specialDemands) {
        this.specialDemands = specialDemands;
    }

    public Reservation.Status getStatus() {
        return status;
    }

    public void setStatus(Reservation.Status status) {
        this.status = status;
    }

    public String getRejectionComment() {
        return rejectionComment;
    }

    public void setRejectionComment(String rejectionComment) {
        this.rejectionComment = rejectionComment;
    }

    public FeedbackResponseDTO getFeedback() {
        return feedback;
    }

    public void setFeedback(FeedbackResponseDTO feedback) {
        this.feedback = feedback;
    }
    
}
