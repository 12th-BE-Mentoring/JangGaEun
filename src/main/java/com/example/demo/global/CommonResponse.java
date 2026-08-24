package com.example.demo.global;

import com.example.demo.global.error.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CommonResponse {
    public static class Str extends ResponseEntity<Map<java.lang.String, java.lang.String>> {
        public Str(Map<java.lang.String, java.lang.String> map, HttpStatus statusCode){
            super(map, statusCode);
        }
        public Str(java.lang.String key, java.lang.String value, HttpStatus statusCode){
            super(Map.of(key, value), statusCode);
        }
    }
    public static class Error {
        private final HttpStatus status;
        private final java.lang.String errorMessage;
        public Error(ErrorCode errorCode){
            this.status=errorCode.getStatus();
            this.errorMessage=errorCode.getMessage();
        }
    }

    public ResponseEntity<Error> ErrorResponse(ErrorCode errorCode){
        return ResponseEntity.status(errorCode.getStatus()).body(new Error(errorCode));
    }

    public Str MessageResponse(java.lang.String msg, HttpStatus status){
        return new Str("message", msg, status);
    }
    public Str MessageResponse(java.lang.String msg){
        return MessageResponse(msg, HttpStatus.OK);
    }
}
