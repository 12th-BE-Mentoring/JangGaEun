package com.example.demo.domain.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "members")
public class User {
    @Id
    @Builder.Default
    UUID id=UUID.randomUUID();

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String pw;

    UserRole role;
}
