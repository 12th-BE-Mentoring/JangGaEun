package com.example.demo.domain.user.refreshToken;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
}
