package com.example.demoJOOQ.service;

import com.tej.JooQDemo.jooq.sample.model.Tables;
import com.tej.JooQDemo.jooq.sample.model.tables.pojos.Book;
import jdk.nashorn.internal.runtime.Context;
import org.jooq.DSLContext;
import org.jooq.util.xml.jaxb.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    DSLContext context;
    public List<Book> getBooks() {
        return context.selectFrom(Tables.BOOK).fetchInto(Book.class);
    }

    public boolean insertBook(Book book) {
        try {
            context.insertInto(Tables.BOOK, Tables.BOOK.TITLE, Tables.BOOK.AUTHOR)
                    .values(book.getTitle(), book.getAuthor())
                    .execute();
            return true;
        }catch(Exception e){
            return false;
        }
    }
}

