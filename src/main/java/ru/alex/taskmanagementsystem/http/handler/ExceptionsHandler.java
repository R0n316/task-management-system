package ru.alex.taskmanagementsystem.http.handler;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import ru.alex.taskmanagementsystem.dto.ErrorDetails;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> handleValidationExceptions(
            MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        String path = request.getDescription(false).replace("uri=", "");
        ErrorDetails errorDetails = new ErrorDetails(System.currentTimeMillis(),
                "Validation Failed",
                errors,
                BAD_REQUEST.value(),
                path);
        return new ResponseEntity<>(errorDetails, BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorDetails> handleBadCredentialsException(
            BadCredentialsException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(System.currentTimeMillis(),
                "Authentication Failed",
                Map.of("message", ex.getMessage()),
                UNAUTHORIZED.value(),
                request.getDescription(false).replace("uri=", ""));
        return new ResponseEntity<>(errorDetails, UNAUTHORIZED);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorDetails> handleDataIntegrityViolationException(
            DataIntegrityViolationException ex, WebRequest request) {
        String errorMessage = "User with this email already exists";
        if (ex.getMessage().contains("users_email_key")) {
            String email = ex.getMessage().split("\\(")[1].split("\\)")[0];
            errorMessage = "User with email \"" + email + "\" already exists";
        }
        ErrorDetails errorDetails = new ErrorDetails(System.currentTimeMillis(),
                "Data Integrity Violation",
                Map.of("message", errorMessage),
                CONFLICT.value(),
                request.getDescription(false).replace("uri=", ""));
        return new ResponseEntity<>(errorDetails, CONFLICT);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleEntityNotFoundException(
            EntityNotFoundException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                System.currentTimeMillis(),
                "Entity not found",
                Map.of("message",ex.getMessage()),
                BAD_REQUEST.value(),
                request.getDescription(false).replace("uri=",""));
        return new ResponseEntity<>(errorDetails,BAD_REQUEST);
    }

}