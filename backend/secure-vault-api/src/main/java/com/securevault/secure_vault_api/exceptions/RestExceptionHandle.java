package com.securevault.secure_vault_api.exceptions;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class RestExceptionHandle {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> handleResourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        StandardError error = new StandardError();
        error.setTimestamp(Instant.now());
        error.setStatus(status.value());
        error.setMessage(ex.getMessage());
        error.setPath(request.getRequestURI());
        error.setMessage("Resource Not Found");

        System.err.println("ERRO INESPERADO: " + ex.getMessage());
        return ResponseEntity.status(status).body(error);

    }

    @ExceptionHandler(ExistingObject.class)
    public ResponseEntity<StandardError> handleResourceExistingObject(ExistingObject ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        StandardError error = new StandardError();
        error.setTimestamp(Instant.now());
        error.setStatus(status.value());
        error.setMessage(ex.getMessage());
        error.setPath(request.getRequestURI());
        error.setMessage("Object Already Exists");

        System.err.println("ERRO INESPERADO: " + ex.getMessage());
        return ResponseEntity.status(status).body(error);

    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<StandardError> handleAccessDeniedException(AccessDeniedException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.FORBIDDEN;

        StandardError error = new StandardError();
        error.setTimestamp(Instant.now());
        error.setStatus(status.value());
        error.setMessage(ex.getMessage());
        error.setPath(request.getRequestURI());
        error.setMessage("You are not allowed to access this resource.");

        System.err.println("ERRO INESPERADO: " + ex.getMessage());
        return ResponseEntity.status(status).body(error);

    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardError> handleException(Exception ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        StandardError error = new StandardError();
        error.setTimestamp(Instant.now());
        error.setStatus(status.value());
        error.setMessage(ex.getMessage());
        error.setPath(request.getRequestURI());
        error.setMessage("Internal Server Error");

        System.err.println("ERRO INESPERADO: " + ex.getMessage());
        return ResponseEntity.status(status).body(error);
    }

}
