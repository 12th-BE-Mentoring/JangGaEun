package com.example.demo.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    final private UserRepository userRepository;

    public void signUp(String name, String pw){
        User user=new User();
        user.name=name;
        user.pw=pw;
        userRepository.save(user);
    }

    public String signIn(String name, String pw){
        User user=userRepository.findByName(name).orElseThrow();
        if(user.pw.equals(pw)){
            throw new IllegalArgumentException();
        }
        return "로그인 성공";
    }
}
