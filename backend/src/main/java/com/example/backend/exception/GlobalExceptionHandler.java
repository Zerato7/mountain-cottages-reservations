package com.example.backend.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<FormFieldErrorMessage> > handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<FormFieldErrorMessage> errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> 
            errors.add(
                new FormFieldErrorMessage(
                    error.getField(), 
                    error.getDefaultMessage()
                )
            )
        );
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<FormFieldErrorMessage> handleDuplicateUserException(DuplicateUserException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
            new FormFieldErrorMessage(ex.getField(), ex.getMessage())
        );
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ErrorMessage> handleAuthException(AuthException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
            new ErrorMessage(ex.getMessage())
        );
    }

}
