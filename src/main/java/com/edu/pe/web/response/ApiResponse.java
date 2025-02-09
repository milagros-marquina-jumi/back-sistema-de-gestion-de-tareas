package com.edu.pe.web.response;

import java.time.LocalDateTime;

public class ApiResponse<T> {
    private String message;  // Mensaje informativo
    private T data;          // Datos de la respuesta (pueden ser objetos)
    private boolean status;  // Indica si la respuesta fue exitosa (true) o fallida (false)
    private String timestamp;    // Fecha y hora de la respuesta

    public ApiResponse(String message, T data) {
        this.message = message;
        this.data = data;
        this.status = true; 
        this.timestamp = LocalDateTime.now().toString();
    }
    
    public ApiResponse(String message, T data, boolean status) {
        this.message = message;
        this.data = data;
        this.status = status; 
        this.timestamp = LocalDateTime.now().toString();
    }

    public ApiResponse(String message, boolean status) {
        this.message = message;
        this.status = status;
        this.timestamp = LocalDateTime.now().toString();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
}

