package com.example.backend.dto.RequestDTO;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class RateReservationDTO {
    
    @NotNull(message = "ID резервације је обавезан")
    private Long reservationId;

    @NotNull(message = "Оцена је обавезна")
    @Min(value = 1, message = "Оцена мора бити у интервалу [1,5]")
    @Max(value = 5, message = "Оцена мора бити у интервалу [1,5]")
    private Integer rating;

    @Length(max = 1000, message = "Дужина коментара је ограничена на 1000 карактера")
    private String comment;

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

}
