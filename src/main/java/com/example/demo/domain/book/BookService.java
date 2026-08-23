package com.example.demo.domain.book;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {
    final private BookRepository bookRepository;
    public String bookCreate(String name, int price){
        Book book=Book.builder()
                .price(price)
                .name(name)
                .build();
        bookRepository.save(book);
        return "책 생성이 성공되었습니다";
    }

}
