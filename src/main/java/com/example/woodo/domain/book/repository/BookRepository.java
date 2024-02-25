package com.example.woodo.domain.book.repository;

import com.example.woodo.domain.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long>, BookRepositoryCustom {
    boolean existsByIsbn(String isbn);
}
