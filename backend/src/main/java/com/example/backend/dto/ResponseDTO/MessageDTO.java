package com.example.backend.dto.ResponseDTO;

public class MessageDTO {
    
    private String message;

    public MessageDTO(String message) {
        this.message = message;
    }

    // Getter for message

    public String getMessage() {
        return message;
    }

}
