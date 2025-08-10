package com.example.backend.dto.ResponseDTO;

import com.example.backend.db.model.UserStatus;
import com.example.backend.db.model.UserType;

public class UserResponseDTO {
            
    private Long id;
    private String username;
    private UserType userType;
    private UserStatus status;

    // Getters and Setters

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public UserType getUserType() {
        return userType;
    }
    public void setUserType(UserType userType) {
        this.userType = userType;
    }
    public UserStatus getStatus() {
        return status;
    }
    public void setStatus(UserStatus status) {
        this.status = status;
    }

}
