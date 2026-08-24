package com.example.demo.global.error;

import com.example.demo.global.ResponseJson;
import com.example.demo.global.error.exception.CustomException;
import com.example.demo.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ResponseJson> customExceptionHandling(CustomException e) {
        final ErrorCode errorCode = e.getErrorCode();
        return ResponseJson.error(errorCode);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseJson> exceptionHandling(IllegalArgumentException e){
        return ResponseJson.error(ErrorCode.UN_EXCEPTION);
    }
}
