package com.example.demo.global.security.jwt;

public record JwtProperties(
        String secretKey,
        Long accessExp,
        Long refreshExp,
        String header,
        String prefix
) {
}
