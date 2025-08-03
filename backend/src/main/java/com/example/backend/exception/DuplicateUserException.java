package com.example.backend.exception;

public class DuplicateUserException extends RuntimeException {
    
    private final String field;

    public DuplicateUserException(String message, String field) {
        super(message);
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
