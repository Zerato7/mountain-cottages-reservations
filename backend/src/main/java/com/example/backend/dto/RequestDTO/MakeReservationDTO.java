package com.example.backend.dto.RequestDTO;

import java.time.OffsetDateTime;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class MakeReservationDTO {
    
    @NotNull(message = "Број одраслих је обавезан.")
    @Min(value = 1, message = "Број одраслих мора бити позитиван цео број.")
    private Integer adults;

    @NotNull(message = "Број деце је обавезан.")
    @Min(value = 0, message = "Број деце мора бити ненегативан цео број.")
    private Integer children;

    private String creditCardNumber;

    @NotNull(message = "Датум и време краја резервације је обавезно.")
    private OffsetDateTime endDateTime;
    
    @NotNull(message = "Датум и време старта резервације је обавезан.")
    private OffsetDateTime startDateTime;
    
    private String specialDemands;
    
    @NotNull(message = "Викендица је обавезна.")
    private Long cottageId;

    @NotNull(message = "Туриста је обавезан.")
    private Long touristId;

    @NotNull(message = "Цена је обавезна.")
    @DecimalMin(value = "0.0", inclusive = false, message = "Цена мора бити позитиван реалан број.")
    private Double cost;

    // Getters and Setters

    public Integer getAdults() {
        return adults;
    }

    public void setAdults(Integer adults) {
        this.adults = adults;
    }

    public Integer getChildren() {
        return children;
    }

    public void setChildren(Integer children) {
        this.children = children;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public OffsetDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(OffsetDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public OffsetDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(OffsetDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getSpecialDemands() {
        return specialDemands;
    }

    public void setSpecialDemands(String specialDemands) {
        this.specialDemands = specialDemands;
    }

    public Long getCottageId() {
        return cottageId;
    }

    public void setCottageId(Long cottageId) {
        this.cottageId = cottageId;
    }

    public Long getTouristId() {
        return touristId;
    }

    public void setTouristId(Long touristId) {
        this.touristId = touristId;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

}
