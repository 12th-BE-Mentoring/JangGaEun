package com.example.demo.user.presentation;

import com.example.demo.user.UserService;
import com.example.demo.user.presentation.DTO.SignInDTO;
import com.example.demo.user.presentation.DTO.SignUpDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    final private UserService userService;

    @PostMapping
    public void signUp(@RequestBody SignUpDTO signUpDTO){
        userService.signUp(signUpDTO.name(), signUpDTO.pw());
    }
    @PostMapping("/signIn")
    public String signIn(@RequestBody SignInDTO signInDTO){
        return userService.signIn(signInDTO.name(),signInDTO.pw());
    }
}
