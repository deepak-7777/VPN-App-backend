package com.securevpn.backend.exception;

import com.securevpn.backend.dto.response.BaseApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

/**
 * Global exception handler — returns consistent JSON error responses.
 * All responses use the BaseApiResponse wrapper to match Android frontend.
 *
 * Privacy note: Error messages never include user data, device info, or traffic details.
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<BaseApiResponse<Void>> handleNotFound(ResourceNotFoundException ex) {
        log.warn("Resource not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(BaseApiResponse.error(ex.getMessage()));
    }

    @ExceptionHandler(ServerUnavailableException.class)
    public ResponseEntity<BaseApiResponse<Void>> handleServerUnavailable(ServerUnavailableException ex) {
        log.warn("Server unavailable: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(BaseApiResponse.error(ex.getMessage()));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BaseApiResponse<Void>> handleBadRequest(BadRequestException ex) {
        log.warn("Bad request: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(BaseApiResponse.error(ex.getMessage()));
    }

    /** Validation errors from @Valid annotations */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseApiResponse<Void>> handleValidation(MethodArgumentNotValidException ex) {
        String errors = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(BaseApiResponse.error("Validation failed: " + errors));
    }

    /** Catch-all — never expose internal stack traces */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseApiResponse<Void>> handleGeneric(Exception ex) {
        log.error("Unhandled exception: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(BaseApiResponse.error("An internal error occurred. Please try again."));
    }
}
