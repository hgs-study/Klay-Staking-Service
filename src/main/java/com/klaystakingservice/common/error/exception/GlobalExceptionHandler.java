package com.klaystakingservice.common.error.exception;

import com.klaystakingservice.common.error.code.ErrorCode;
import com.klaystakingservice.common.error.response.ErrorResponse;
import com.klaystakingservice.common.response.util.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.klaystakingservice.common.error.code.ErrorCode.EMAIL_NOT_FOUND;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

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

    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<?> EmailNotFoundException(EmailNotFoundException e) {


        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("존재하지 않는 계정이거나 패스워드가 일치하지 않습니다.");
    }

//    @ExceptionHandler(AuthenticationException.class)
//    public ResponseEntity<?> AuthenticationException(AuthenticationException e) {
//        log.debug("====AuthenticationException start====");
//
//        return ResponseEntity.status(HttpStatus.FORBIDDEN)
//                .body("존재하지 않는 계정이거나 패스워드가 일치하지 않습니다.");
//    }

}
