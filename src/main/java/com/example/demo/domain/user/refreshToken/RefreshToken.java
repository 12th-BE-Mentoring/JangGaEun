package com.example.demo.domain.user.refreshToken;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshToken {
    @Id
    String accountId;
    String tokenHash;
    Date expiresAt;
}
