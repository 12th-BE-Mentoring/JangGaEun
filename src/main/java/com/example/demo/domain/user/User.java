package com.example.demo.domain.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    UUID id=UUID.randomUUID();

    String name;
    String pw;
}
