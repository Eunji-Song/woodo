package com.example.woodo.common.exception.rental;

import com.example.woodo.common.apiresult.ApiResultCode;
import com.example.woodo.common.exception.ApplicationException;
import com.example.woodo.common.exception.ConflictException;

public class BookAlreadyRentedException extends ConflictException {
    public BookAlreadyRentedException(String title) {
        super("'" + title + "' 도서는 현재 대여가 불가능 합니다. ");
    }
}
