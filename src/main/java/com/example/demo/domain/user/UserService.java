package com.example.demo.domain.user;

import com.example.demo.domain.user.presentation.DTO.JoinDTO;
import com.example.demo.domain.user.presentation.DTO.LoginDTO;
import com.example.demo.global.error.exception.CustomException;
import com.example.demo.global.error.exception.ErrorCode;
import com.example.demo.global.response.dto.ResponseTokenDTO;
import com.example.demo.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public void join(JoinDTO dto){
        if (!dto.pw().equals(dto.checkPw())){
            throw new CustomException(ErrorCode.NOT_EQUALS_PASSWORD);
        }
        User user= User.builder()
                .pw(passwordEncoder.encode(dto.pw()))
                .name(dto.name())
                .role(dto.role())
                .build();
        userRepository.save(user);
    }

    public ResponseTokenDTO login(LoginDTO dto){
        User user=userRepository.findByName(dto.name())
                .orElseThrow(()->new CustomException(ErrorCode.FALSE_LOGIN));
        if(passwordEncoder.matches(dto.pw(), user.pw)){
            throw new CustomException(ErrorCode.FALSE_LOGIN);
        }
        return jwtTokenProvider.createJwt(user.id.toString(), Map.of("role", user.role));
    }
}
