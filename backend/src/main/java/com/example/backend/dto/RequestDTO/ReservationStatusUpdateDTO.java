package com.example.backend.dto.RequestDTO;

import jakarta.validation.constraints.NotNull;

public class ReservationStatusUpdateDTO {
    
    @NotNull(message = "UD резервације је обавезан.")
    private Long id;

    private String rejectionComment;

    // Getters and Setters
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRejectionComment() {
        return rejectionComment;
    }

    public void setRejectionComment(String rejectionComment) {
        this.rejectionComment = rejectionComment;
    }

}
