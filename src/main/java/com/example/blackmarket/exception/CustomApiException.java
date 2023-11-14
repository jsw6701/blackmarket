package com.example.blackmarket.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomApiException extends RuntimeException{
    
    private HttpStatus status;

    public CustomApiException(String message) {
        this(message, HttpStatus.BAD_REQUEST);
    }
    public CustomApiException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
