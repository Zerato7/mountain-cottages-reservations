package com.example.backend.exception;

public class FormFieldErrorMessage {

    private String field;
    private String message;

    public FormFieldErrorMessage(String field, String message) {
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
