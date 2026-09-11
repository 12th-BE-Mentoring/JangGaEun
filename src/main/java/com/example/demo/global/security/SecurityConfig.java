package com.example.demo.global.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

@Configuration
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //filter chain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
        //csrf설정
        http.csrf(csrf->csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/user/**").permitAll()
                        .requestMatchers(HttpMethod.POST , "/book").hasRole("MANAGER") //새로운 책 등록을 매니저만 허용
                        .requestMatchers("/book/**").authenticated() //책 빌리기 기능은 로그인 사용자만 허용
                );

        return http.build();
    }
}
