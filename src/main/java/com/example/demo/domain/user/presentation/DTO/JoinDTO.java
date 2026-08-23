package com.example.demo.domain.user.presentation.DTO;

public record JoinDTO(
        String name,
        String pw,
        String checkPw
) {
}
