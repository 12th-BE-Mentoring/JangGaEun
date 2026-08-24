package com.example.demo.global.error;

import com.example.demo.global.CommonResponse;
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
    private final CommonResponse commonResponse;

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CommonResponse.Error> customExceptionHandling(CustomException e) {
        final ErrorCode errorCode = e.getErrorCode();
        return commonResponse.ErrorResponse(errorCode);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public CommonResponse.Str exceptionHandling(IllegalArgumentException e){
        return commonResponse.MessageResponse("Unexception error", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
