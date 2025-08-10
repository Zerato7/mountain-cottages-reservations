package com.example.backend.dto.ResponseDTO;

public class FormFieldErrorMessageDTO {

    private String field;
    private String message;

    public FormFieldErrorMessageDTO(String field, String message) {
        this.field = field;
        this.message = message;
    }

    // Getters for field and message

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }
    
}
