package com.myKart.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private final LocalDateTime timestamp;
    private final boolean success;
    private final T data;
    private final ApiError error;

    // Constructor for success response
    public ApiResponse(T data) {
        this.timestamp = LocalDateTime.now();
        this.success = true;
        this.data = data;
        this.error = null;
    }

    // Constructor for error response
    public ApiResponse(ApiError error) {
        this.timestamp = LocalDateTime.now();
        this.success = false;
        this.data = null;
        this.error = error;
    }

    // Getters
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public boolean isSuccess() {
        return success;
    }

    public T getData() {
        return data;
    }

    public ApiError getError() {
        return error;
    }
}