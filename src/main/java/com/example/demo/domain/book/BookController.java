package com.example.demo.domain.book;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {
    final private BookService bookService;

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createBook(@RequestParam String name, @RequestParam int price){
        return bookService.bookCreate(name, price);
    }

    @PatchMapping("/borrow/{bookId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void borrowBook(
            @RequestHeader("Authorization") String token,
            @PathVariable Long bookId
    ){
        bookService.bookBorrow(token, bookId);
    }
}
