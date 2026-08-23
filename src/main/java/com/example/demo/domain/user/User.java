package com.example.demo.domain.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @Builder.Default
    UUID id=UUID.randomUUID();

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String pw;
}
