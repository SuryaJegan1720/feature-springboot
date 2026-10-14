package com.memory.h2memory.repository;

import com.memory.h2memory.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepos extends JpaRepository<Book, Long>{

    List<Book> findBooksByAuthor(String author);
}
