package com.example.demo.domain.book;

import com.example.demo.domain.user.User;
import com.example.demo.domain.user.UserRepository;
import com.example.demo.global.error.exception.CustomException;
import com.example.demo.global.error.exception.ErrorCode;
import com.example.demo.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = CustomException.class)
public class BookService {
    final private BookRepository bookRepository;
    private final UserRepository userRepository;
    private final JwtTokenProvider tokenProvider;
    public String bookCreate(String name, int price){
        Book book=Book.builder()
                .price(price)
                .name(name)
                .build();
        bookRepository.save(book);
        return "책 생성이 성공되었습니다";
    }

    public void bookBorrow(String token, Long bookId){
        Book book = bookRepository.findById(bookId)
                .orElseThrow(()-> new CustomException(ErrorCode.BOOK_NOT_FOUND));
        if(!(book.getUser() ==null)){
            throw new CustomException(ErrorCode.ANOTHER_USER_OWNED);
        }
        User user = userRepository.findById(UUID.fromString(tokenProvider.getSub(token)))
                .orElseThrow(()-> new CustomException(ErrorCode.USER_NOT_FOUND));
        book.setUser(user);
    }
}
