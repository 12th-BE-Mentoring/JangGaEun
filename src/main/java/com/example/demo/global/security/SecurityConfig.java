package com.example.demo.global.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

@Configuration
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public static String hashToken(String refreshToken) throws Exception {
        //자바 scanner 해싱 버전
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        //utf-8방식으로 byte[]배열 얻은 것으로 해싱
        byte[] hash = digest.digest(refreshToken.getBytes(StandardCharsets.UTF_8));

        // Byte 배열을 16진수 문자열로 변환
        // byte[]를 char[]로 변환 후 Str 생성
        return new String(Hex.encode(hash));
    }
}
