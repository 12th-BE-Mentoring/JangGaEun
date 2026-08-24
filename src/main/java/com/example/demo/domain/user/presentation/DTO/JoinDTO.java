package com.example.demo.domain.user.presentation.DTO;

import com.example.demo.domain.user.UserRole;

public record JoinDTO(
        String name,
        String pw,
        String checkPw,
        UserRole role
) {
}
