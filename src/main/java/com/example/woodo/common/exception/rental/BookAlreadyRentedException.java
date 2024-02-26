package com.example.woodo.common.exception.rental;

import com.example.woodo.common.apiresult.ApiResultCode;
import com.example.woodo.common.exception.ApplicationException;
import com.example.woodo.common.exception.ConflictException;

public class BookAlreadyRentedException extends ConflictException {
    public BookAlreadyRentedException() {
        super(ApiResultCode.ALREADY_RENTED_BOOK.getMessage());
    }

    public BookAlreadyRentedException(String message) {
        super(message);
    }

    @Override
    public ApiResultCode getResultCode() {
        return ApiResultCode.ALREADY_RENTED_BOOK;
    }
}
