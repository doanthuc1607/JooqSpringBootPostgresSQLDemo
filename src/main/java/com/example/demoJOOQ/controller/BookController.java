package com.example.demoJOOQ.controller;

import com.example.demoJOOQ.service.BookService;
import com.tej.JooQDemo.jooq.sample.model.tables.pojos.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/books")
public class BookController {
    @Autowired
    BookService bookService;
    @GetMapping
    public ResponseEntity<List<Book>> getBooks(){
        return ResponseEntity.ok(this.bookService.getBooks());
    }
    @PostMapping
    public boolean postBook(@RequestBody Book book){
        return this.bookService.insertBook(book);
    }


}
