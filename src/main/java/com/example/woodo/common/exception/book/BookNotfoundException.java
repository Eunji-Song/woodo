package com.example.woodo.common.exception.book;

import com.example.woodo.common.apiresult.ApiResultCode;
import com.example.woodo.common.exception.NotFoundException;

public class BookNotfoundException extends NotFoundException {
    public BookNotfoundException() {
        super(ApiResultCode.NOTFOUND_BOOK.getMessage());
    }

    public BookNotfoundException(String message) {
        super(message);
    }

    @Override
    public ApiResultCode getResultCode() {
        return ApiResultCode.NOTFOUND_BOOK; 
    }
}
