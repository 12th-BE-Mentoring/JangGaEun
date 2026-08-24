package com.example.demo.domain.user;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserRole {
    USER("ROLE_USER", false),
    MANAGER("ROLE_MANAGER", true);

    private final String role;
    private final boolean isManager;

}
