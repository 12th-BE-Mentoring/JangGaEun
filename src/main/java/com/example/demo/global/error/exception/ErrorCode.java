package com.example.demo.global.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "server error"),
    // jwt
    EXPIRED_JWT(HttpStatus.UNAUTHORIZED, "Expired JWT"),
    INVALID_JWT(HttpStatus.UNAUTHORIZED, "Invalid JWT"),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "Invalid Refresh Token"),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "Refresh token Not Found"),

    USER_NOT_FOUND(HttpStatus.NOT_FOUND,"user not found"),
    FALSE_LOGIN(HttpStatus.UNAUTHORIZED, "login is false"),
    NOT_EQUALS_PASSWORD(HttpStatus.BAD_REQUEST, "is not equals password");


    private final HttpStatus status;
    private final String message;
}
