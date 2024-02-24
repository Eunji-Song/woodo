package com.example.woodo.common.exception.book;

import com.example.woodo.common.exception.ConflictException;

public class BookConflictException extends ConflictException {
    public BookConflictException(String isbn) {
        super("이미 위탁이 완료된 도서입니다.");
    }
}
