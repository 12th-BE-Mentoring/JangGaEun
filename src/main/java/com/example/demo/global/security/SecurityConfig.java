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

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
        http.csrf(csrf->csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/user/**").permitAll()
                        .requestMatchers(HttpMethod.POST , "/book").hasRole("MANAGER")
                        .requestMatchers("/book/**").authenticated()
                );

        return http.build();
    }
}
