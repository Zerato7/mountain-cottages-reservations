package com.example.backend.dto.RequestDTO;

import jakarta.validation.constraints.NotBlank;

public class UserLoginDTO {
    
    @NotBlank(message = "Корисничко име је обавезно.")
    private String username;

    @NotBlank(message = "Лозинка је обавезна.")
    private String password;

    // Getters and Setters

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
