package com.example.game.exception;

import org.springframework.http.HttpStatus;

public interface GlobalExceptionType {
    int getErrorCode();

    HttpStatus getHttpStatus();

    String getErrorMessage();
//
}
