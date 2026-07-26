package com.example.demo.domain.book;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {
    final private BookService bookService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createBook(@RequestParam String name, @RequestParam int price){
        return bookService.bookCreate(name, price);
    }
}
