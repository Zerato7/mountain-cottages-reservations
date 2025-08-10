package com.example.backend.dto.RequestDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PasswordChangeDTO {

    @NotNull(message = "ID корисника је обавезан.")
    private Long id;
    @NotBlank(message = "Тренутна лозинка је обавезна.")
    private String currentPassword;
    @NotBlank(message = "Нова лозинка је обавезна.")
    private String newPassword;

    // Getters and Setters

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getCurrentPassword() {
        return currentPassword;
    }
    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }
    public String getNewPassword() {
        return newPassword;
    }
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
    
}
