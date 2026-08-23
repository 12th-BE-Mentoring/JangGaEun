package com.example.demo.domain.user.presentation;

import com.example.demo.domain.user.UserService;
import com.example.demo.domain.user.presentation.DTO.LoginDTO;
import com.example.demo.domain.user.presentation.DTO.JoinDTO;
import com.example.demo.global.response.dto.ResponseTokenDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    final private UserService userService;

    @PostMapping("/join")
    public void join(@RequestBody JoinDTO joinDTO){
        userService.join(joinDTO);
    }
    @PostMapping("/login")
    public ResponseTokenDTO login(@RequestBody LoginDTO loginDTO){
        return userService.login(loginDTO);
    }
}
