package com.example.blackmarket.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException {

    private HttpStatus status;

    public CustomException(String message) {
        this(message, HttpStatus.BAD_REQUEST);
    }
    public CustomException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
