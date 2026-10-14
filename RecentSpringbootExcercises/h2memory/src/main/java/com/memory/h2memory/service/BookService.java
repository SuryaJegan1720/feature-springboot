package com.memory.h2memory.service;

import com.memory.h2memory.model.Book;
import com.memory.h2memory.repository.BookRepos;
import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class BookService {

    private final BookRepos books;

    public BookService(BookRepos books) {
        this.books = books;
    }

    public Book create(Book book) {
        return books.save(book);
    }

    public List<Book> getAll() {
      return  books.findAll();
    }

    public Book getById(Long id) {
        return books.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Book not found"));
    }

    public Book update(Long id, Book input) {
        Book book = getById(id);
        book.setTitle(input.getTitle());
        return books.save(book);
    }

    public void delete(Long id) {
        if (!books.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Book not found");
        }
        books.deleteById(id);
    }

    public  List<Book> getBooksByAuthor(String author) {
        return books.findBooksByAuthor(author);
    }
}
