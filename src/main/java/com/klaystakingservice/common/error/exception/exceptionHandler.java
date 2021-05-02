package com.klaystakingservice.common.error.exception;

import com.klaystakingservice.common.error.code.ErrorCode;
import com.klaystakingservice.common.error.response.ErrorResponse;
import com.klaystakingservice.common.response.util.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class exceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> BusinessException(BusinessException e){
        ErrorResponse errorResponse = ErrorResponse.of(e.getErrorCode());
        ErrorCode errorCode = e.getErrorCode();
        errorResponse.setMessage(errorCode.getMessage());
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.valueOf(errorCode.getStatus()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> MethodArgumentNotValidException(MethodArgumentNotValidException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.message(e.getBindingResult().getFieldError().getDefaultMessage()));
    }

}
