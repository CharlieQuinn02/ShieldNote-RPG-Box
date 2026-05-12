package br.ifg.urt.shieldnoterpgbox.exception;

import java.time.LocalDateTime;

public class ExceptionResponse {
    
    private LocalDateTime timestamp;
    private String message;
    private String details;

    // construtor vazio 
    public ExceptionResponse() {
    }

    // construtor com todos os parâmetros
    public ExceptionResponse(LocalDateTime timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    // getters e setters 
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}