package com.example.MoneyTransfer.exception.response;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ErrorDetails {
    private LocalDateTime timestamp;
    private String message;
    private String details;
    private String httpStatus;

    public ErrorDetails(LocalDateTime timestamp, String message, String details, String httpStatus) {
        super();
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
        this.httpStatus = httpStatus;
    }

    public ErrorDetails(LocalDateTime now, String message, HttpStatus httpStatus) {
        super();
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
        this.httpStatus = String.valueOf(httpStatus);
    }


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

    public String getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(String httpStatus) {
        this.httpStatus = httpStatus;
    }
}
