package com.sistema_estoque.estoque.infra;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import com.sistema_estoque.estoque.exceptions.ProductNotFoundException;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ControllerAdvicer {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> handleProductNotFoundException(ProductNotFoundException ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(400).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        return ResponseEntity.status(500).body("Error: " + ex.getMessage());
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<String> handleMethodValidationException(HandlerMethodValidationException ex) {
        return ResponseEntity.status(400).body("Error: " + ex.getMessage());
    }
}
