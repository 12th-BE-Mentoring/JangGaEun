package com.example.demo.global;

import com.example.demo.global.error.exception.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@Getter
@Builder
public class ResponseJson {
    private boolean success;
    private Object data;

    public static ResponseJson success(Object data){
        return new ResponseJson(true, data);
    }

    public static ResponseEntity<ResponseJson> error(ErrorCode status){
        return ResponseEntity
                .status(status.getStatus())
                .body(new ResponseJson(false, Map.of(
                    "status", status.getStatus(),
                    "message", status.getMessage()
        )));
    }
}
