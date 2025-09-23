// 📂 src/main/java/com/nabiji/ecommerce/exception/GlobalExceptionHandler.java
package com.nabiji.ecommerce.exception;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Hidden
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionBody> handleResourceNotFound(ResourceNotFoundException ex) {
        ExceptionBody body = new ExceptionBody(HttpStatus.NOT_FOUND.value(), "Resource Not Found", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({InsufficientBalanceException.class, CartEmptyException.class})
    public ResponseEntity<ExceptionBody> handleBadRequest(RuntimeException ex) {
        ExceptionBody body = new ExceptionBody(HttpStatus.BAD_REQUEST.value(), "Bad Request", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<ExceptionBody> handleConflict(InsufficientStockException ex) {
        ExceptionBody body = new ExceptionBody(HttpStatus.CONFLICT.value(), "Conflict", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    // Fallback handler for any other unhandled runtime exception
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionBody> handleGenericRuntimeException(RuntimeException ex) {
        // Important to log the full stack trace for debugging
        ex.printStackTrace();
        ExceptionBody body = new ExceptionBody(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error", "An unexpected error occurred. Please contact support.");
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}