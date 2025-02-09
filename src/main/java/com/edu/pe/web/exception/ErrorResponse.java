package com.edu.pe.web.exception;

import java.time.LocalDateTime;

public class ErrorResponse {

    private LocalDateTime timestamp;   // Fecha y hora del error
    private int status;                // Código de estado HTTP
    private String error;              // Descripción del tipo de error
    private String message;            // Mensaje detallado del error
    private String path;               // Endpoint donde ocurrió el error
    private String exception;          // Nombre de la excepción que ocurrió
    private Object details;            // Detalles adicionales, como errores de validación
    private String technicalMessage;   // Mensaje técnico adicional (opcional)
    
    public ErrorResponse(int status, String error, String message, String path, String exception, Object details, String technicalMessage) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        this.exception = exception;
        this.details = details;
        this.technicalMessage = technicalMessage; // Nuevo campo
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public Object getDetails() {
        return details;
    }

    public void setDetails(Object details) {
        this.details = details;
    }
    
    public String getTechnicalMessage() {
        return technicalMessage;
    }

    public void setTechnicalMessage(String technicalMessage) {
        this.technicalMessage = technicalMessage;
    }
}
