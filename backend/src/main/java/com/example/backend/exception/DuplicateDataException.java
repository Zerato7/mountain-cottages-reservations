package com.example.backend.exception;

public class DuplicateDataException extends RuntimeException {
    
    private final String field;

    public DuplicateDataException(String message, String field) {
        super(message);
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
