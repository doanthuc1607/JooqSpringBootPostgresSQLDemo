package com.example.demoJOOQ.controller;

import com.example.demoJOOQ.respone.BookRespone;
import com.example.demoJOOQ.service.BookService;
import com.tej.JooQDemo.jooq.sample.model.tables.pojos.Book;
import org.jooq.Record3;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import request.BookRequest;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/books")
public class BookController {
    @Autowired
    BookService bookService;

    @GetMapping
    public ResponseEntity<List<Book>> getBooks() {
        return ResponseEntity.ok(this.bookService.getBooks());
    }

    @PostMapping
    public boolean postBook(@RequestBody Book book) {
        return this.bookService.insertBook(book);
    }

    @PutMapping("{bookId}")
    public boolean putBook(@RequestBody Book book, @PathVariable Integer bookId){
        return bookService.updateBook(book, bookId);
    }

    //delete record has id > 3
    @DeleteMapping("{idCondition}")
    public boolean deleteBook(@PathVariable Integer idCondition){
        return bookService.deleteBookWithIDMoreCondition(idCondition);
    }

    //Dùng case when
    //Hiển thị tình trạng hàng hóa còn trong kho
    @GetMapping("inventory")
    public Map<BookRespone, String> updateData(){
        return bookService.displayInventoryOfProduct();
    }




}
