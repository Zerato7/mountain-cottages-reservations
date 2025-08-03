package com.example.backend.dto.ResponseDTO;

import com.example.backend.db.model.UserType;

public class UserResponseDTO {
            
    private String username;
    private UserType userType;

    // Getters and Setters

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

}
