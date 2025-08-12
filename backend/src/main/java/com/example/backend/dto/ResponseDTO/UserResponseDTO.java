package com.example.backend.dto.ResponseDTO;

import com.example.backend.db.model.User;

public class UserResponseDTO {
            
    private Long id;
    private String username;
    private User.Type userType;
    private User.Status status;

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

    public User.Type getUserType() {
        return userType;
    }

    public void setUserType(User.Type userType) {
        this.userType = userType;
    }

    public User.Status getStatus() {
        return status;
    }
    
    public void setStatus(User.Status status) {
        this.status = status;
    }

}
