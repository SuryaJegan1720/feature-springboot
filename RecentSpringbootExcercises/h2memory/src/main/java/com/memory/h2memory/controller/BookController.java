package com.memory.h2memory.controller;

import com.memory.h2memory.model.Book;
import com.memory.h2memory.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    @Autowired
    BookService bookService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Book create(@RequestBody Book book) {
        return bookService.create(book);
    }

    @GetMapping("/all")
    public List<Book> getAll() {
        return bookService.getAll();
    }

    @GetMapping("/{id}")
    public Book getById(@PathVariable Long id) {
        return bookService.getById(id);
    }

    @PutMapping("/{id}")
    public Book update(@PathVariable Long id, @RequestBody Book book) {
        return bookService.update(id, book);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        bookService.delete(id);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Book>> getBooksByAuthor(
            @RequestParam String author) {

        return ResponseEntity.ok(
                bookService.getBooksByAuthor(author));
    }

}
