package com.example.demo.domain.book;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional findById(Long id);
}
