package com.mykart.seller.exception;

import com.myKart.infra.exception.BussinessException;
import com.mykart.seller.dto.ApiError;
import com.mykart.seller.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BussinessException.class)
    public ResponseEntity<ApiResponse<Object>> handleBusinessException(BussinessException ex) {
        ApiError apiError = new ApiError("BUSINESS_ERROR", ex.getMessage());
        return new ResponseEntity<>(new ApiResponse<>(apiError), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGenericException(Exception ex) {
        ApiError apiError = new ApiError("INTERNAL_SERVER_ERROR", "An unexpected error occurred: " + ex.getMessage());
        return new ResponseEntity<>(new ApiResponse<>(apiError), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}