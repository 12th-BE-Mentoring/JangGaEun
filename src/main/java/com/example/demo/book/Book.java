package com.example.demo.book;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name="books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    private int price;

    private BookStatue statue=BookStatue.SALE;

    public Book() {
    }
    public Book(String name, int price, BookStatue statue){
        this.name=name;
        this.price=price;
        this.statue=statue;
    }


    Long getId(){
        return this.id;
    }
    String getName(){
        return this.name;
    }
    int getPrice(){
        return this.price;
    }

}
