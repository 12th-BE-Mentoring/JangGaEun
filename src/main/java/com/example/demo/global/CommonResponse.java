package com.example.demo.global;

import com.example.demo.global.error.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CommonResponse {
    public class StringResponseEntity extends ResponseEntity<Map<String,String>> {
        public StringResponseEntity(Map<String, String> map, HttpStatus statusCode){
            super(map, statusCode);
        }
        public StringResponseEntity(String key, String value, HttpStatus statusCode){
            super(Map.of(key, value), statusCode);
        }
    }
    public static class ErrorResponseEntity {
        private final HttpStatus status;
        private final String errorMessage;
        public ErrorResponseEntity(ErrorCode errorCode){
            this.status=errorCode.getStatus();
            this.errorMessage=errorCode.getMessage();
        }
    }

    public StringResponseEntity ErrorResponse(ErrorCode errorCode){
        return new StringResponseEntity("error message", errorCode.getMessage(),errorCode.getStatus());
    }

    public StringResponseEntity MessageResponse(String msg, HttpStatus status){
        return new StringResponseEntity("message", msg, status);
    }
    public StringResponseEntity MessageResponse(String msg){
        return MessageResponse(msg, HttpStatus.OK);
    }
}
