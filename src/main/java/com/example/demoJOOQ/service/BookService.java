package com.example.demoJOOQ.service;

import com.example.demoJOOQ.respone.BookRespone;
import com.tej.JooQDemo.jooq.sample.model.Tables;
import com.tej.JooQDemo.jooq.sample.model.tables.pojos.Book;
import com.tej.JooQDemo.jooq.sample.model.tables.records.BookRecord;
import jdk.nashorn.internal.runtime.Context;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Record3;
import org.jooq.Result;
import org.jooq.impl.DSL;
import org.jooq.util.xml.jaxb.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import request.BookRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BookService {
    //create context for your Databe
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
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateBook(Book book, Integer bookId) {
        try {
            context.update(Tables.BOOK).set(Tables.BOOK.TITLE, book.getTitle()).set(Tables.BOOK.AUTHOR, book.getAuthor()).where(Tables.BOOK.ID.eq(bookId)).execute();
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    //xóa các quyển sách có id > condition
    public boolean deleteBookWithIDMoreCondition(Integer idCondition) {
        try{
            context.deleteFrom(Tables.BOOK).where(Tables.BOOK.ID.greaterThan(idCondition)).execute();
            return true;
        }catch(Exception ex){
            ex.printStackTrace();
            return false;
        }
    }





    public Map<BookRespone, String> displayInventoryOfProduct() {
        Map<BookRespone, String> result = new HashMap<>();//có cách nào để chuyển resultset thành đối tượng hay mảng gì mà ko lỗi ko? (tostring thì oke nhưng toArray thì lỗi)
        Result<Record3<Integer, String, String>> resultSet = context.select(Tables.BOOK.ID, Tables.BOOK.TITLE, DSL.when(Tables.BOOK.QUANTITY.eq(0),"Hết hàng")
                                                               .when(Tables.BOOK.QUANTITY.between(1,5),"sắp hết hàng")
                                                                    .when(Tables.BOOK.QUANTITY.greaterThan(5),"còn hàng").as("inventoryStatus")).from(Tables.BOOK).fetch();

        for(Record r: resultSet){
            BookRespone b = new BookRespone();
            b.setId(r.getValue(Tables.BOOK.ID));
            b.setTitle(r.getValue(Tables.BOOK.TITLE));
            result.put(b,r.getValue(DSL.field("inventoryStatus")).toString());//vì nó trả về dối tượng nên là phải convert về KDL mà ta muốn trả
        }
        return result;
    }
}

