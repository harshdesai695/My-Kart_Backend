package com.myKart.user.dto;

public class ApiError {

    private final String errorCode;
    private final String message;

    public ApiError(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    // Getters
    public String getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }
}