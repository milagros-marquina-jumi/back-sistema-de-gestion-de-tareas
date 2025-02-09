package com.edu.pe.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;

import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Manejo de excepciones de validación (422 Bad Request)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(ConstraintViolationException ex, HttpServletRequest request) {
        List<String> errors = ex.getConstraintViolations().stream()
                .map(cv -> cv.getMessage())
                .collect(Collectors.toList());
        String errorMessage = String.join(", ", errors);
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                errorMessage,
                request.getRequestURI(),
                ex.getClass().getSimpleName(),
                errors,
                ex.getMessage() 
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // Manejo de excepciones de tipo MethodArgumentTypeMismatchException (cuando el parámetro no es válido)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
        String errorMessage = String.format("El parámetro '%s' no es válido. Se esperaba un valor de tipo '%s'.", 
                ex.getName(), ex.getRequiredType().getSimpleName());
        
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                errorMessage,
                request.getRequestURI(),
                ex.getClass().getSimpleName(),
                null,
                ex.getMessage() 
        );
        
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // Manejo de excepciones de enlace de datos (Bad Request)
    @ExceptionHandler(org.springframework.validation.BindException.class)
    public ResponseEntity<ErrorResponse> handleBindException(BindingResult bindingResult, HttpServletRequest request) {
        List<String> errors = bindingResult.getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.toList());

        /*
         String errorMessage;
        if (errors.size() == 1) {
            errorMessage = "El siguiente campo es obligatorio: " + errors.get(0);
        } else {
            errorMessage = "Los siguientes campos son obligatorios: " + String.join(", ", errors);
        } 
         */
        
        String errorMessage = "Por favor, corrige los errores en los campos obligatorios.";
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                errorMessage,
                request.getRequestURI(),
                bindingResult.getClass().getSimpleName(),
                errors,
                null
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // Manejo de error cuando no se encuentra un recurso (404 Not Found)
    @ExceptionHandler(jakarta.persistence.EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(jakarta.persistence.EntityNotFoundException ex, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                ex.getMessage(),
                request.getRequestURI(),
                ex.getClass().getSimpleName(),
                null,
                ex.getMessage() 
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    // Manejo de excepciones de tipo ResponseStatusException (para manejar códigos de error como 404)
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleResponseStatusException(ResponseStatusException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.valueOf(ex.getStatusCode().value());
        String message = ex.getReason() != null ? ex.getReason() : "Un error ocurrió";

        ErrorResponse errorResponse = new ErrorResponse(
                status.value(),
                status.getReasonPhrase(),
                message,
                request.getRequestURI(),
                ex.getClass().getSimpleName(),
                null,
                ex.getMessage() 
        );
        return new ResponseEntity<>(errorResponse, status);
    }

    // Manejo de excepciones generales del servidor (500 Internal Server Error)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleInternalServerError(Exception ex, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                "Error interno del servidor: " + ex.getMessage(),
                request.getRequestURI(),
                ex.getClass().getSimpleName(),
                null,
                ex.getMessage() 
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
