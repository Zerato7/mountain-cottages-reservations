package com.example.backend.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.backend.dto.ResponseDTO.MessageDTO;
import com.example.backend.dto.ResponseDTO.MessageListDTO;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MessageListDTO> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<MessageDTO> errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.add(
                new MessageDTO(
                    error.getDefaultMessage()
                )
            );
        });
        return ResponseEntity.badRequest().body(new MessageListDTO(errors));
    }

    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<MessageDTO> handleDuplicateUserException(DuplicateUserException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
            new MessageDTO(ex.getMessage())
        );
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<MessageDTO> handleAuthException(AuthException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
            new MessageDTO(ex.getMessage())
        );
    }

    @ExceptionHandler(PasswordChangeException.class)
    public ResponseEntity<MessageDTO> handlePasswordChangeException(PasswordChangeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            new MessageDTO(ex.getMessage())
        );
    }

    @ExceptionHandler(BackendServerException.class)
    public ResponseEntity<MessageDTO> handleBackendServerException(BackendServerException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
            new MessageDTO(ex.getMessage())
        );
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<MessageDTO> handleBadRequestException(BadRequestException ex) {
        return ResponseEntity.badRequest().body(
            new MessageDTO(ex.getMessage())
        );
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<MessageDTO> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
            new MessageDTO(ex.getMessage())
        );
    }

}
