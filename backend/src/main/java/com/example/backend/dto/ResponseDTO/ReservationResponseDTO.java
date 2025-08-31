package com.example.backend.dto.ResponseDTO;

import java.time.OffsetDateTime;

import com.example.backend.db.model.Reservation;

public class ReservationResponseDTO {
    
    private Long id;
    private String touristFirstname;
    private String touristLastname;
    private String cottageName;
    private String cottageLocation;

    private OffsetDateTime datetimeStart;
    private OffsetDateTime datetimeEnd;
    private Integer adultsNumber;
    private Integer childrenNumber;
    private Double cost;
    private String specialDemands;
    private Reservation.Status status;
    private String rejectionComment;

    private FeedbackResponseDTO feedback;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTouristFirstname() {
        return touristFirstname;
    }

    public void setTouristFirstname(String touristFirstname) {
        this.touristFirstname = touristFirstname;
    }

    public String getTouristLastname() {
        return touristLastname;
    }

    public void setTouristLastname(String touristLastname) {
        this.touristLastname = touristLastname;
    }

    public String getCottageName() {
        return cottageName;
    }

    public void setCottageName(String cottageName) {
        this.cottageName = cottageName;
    }

    public String getCottageLocation() {
        return cottageLocation;
    }

    public void setCottageLocation(String cottageLocation) {
        this.cottageLocation = cottageLocation;
    }

    public OffsetDateTime getDatetimeStart() {
        return datetimeStart;
    }

    public void setDatetimeStart(OffsetDateTime datetimeStart) {
        this.datetimeStart = datetimeStart;
    }

    public OffsetDateTime getDatetimeEnd() {
        return datetimeEnd;
    }

    public void setDatetimeEnd(OffsetDateTime datetimeEnd) {
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
