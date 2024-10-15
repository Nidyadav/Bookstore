package com.example.bookstore.batch;

import com.example.bookstore.model.Book;
import org.springframework.batch.item.ItemProcessor;

public class BookJobProcessor implements ItemProcessor<Book, Book> {
    @Override
    public Book process(Book item) throws Exception {
        return null;
    }
}
