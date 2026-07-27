package com.example.demo.domain.user;

import com.example.demo.global.error.exception.CustomException;
import com.example.demo.global.error.exception.ErrorCode;
import com.example.demo.global.response.dto.ResponseTokenDTO;
import com.example.demo.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public void signUp(String name, String pw){
        User user=new User();
        user.name=name;
        user.pw=passwordEncoder.encode(pw);
        userRepository.save(user);
    }

    public ResponseTokenDTO signIn(String name, String pw){
        User user=userRepository.findByName(name).orElseThrow(()->new CustomException(ErrorCode.FALSE_LOGIN));
        if(passwordEncoder.matches(pw, user.pw)){
            throw new CustomException(ErrorCode.FALSE_LOGIN);
        }
        return jwtTokenProvider.createJwt(user.id.toString());
    }
}
