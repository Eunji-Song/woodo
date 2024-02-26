package com.example.woodo.domain.book.repository;

import com.example.woodo.domain.book.dto.BookDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface BookRepositoryCustom {
    // 목록 조회
    Page<BookDto> findAllBooks(Pageable pageable);
}
