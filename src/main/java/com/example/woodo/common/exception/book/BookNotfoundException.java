package com.example.woodo.common.exception.book;

import com.example.woodo.common.exception.NotFoundException;

public class BookNotfoundException extends NotFoundException {
    public BookNotfoundException() {
        super("존재하지 않는 도서입니다.");
    }

    public BookNotfoundException(String message) {
        super(message);
    }
}
