package com.example.woodo.common.exception.book;

import com.example.woodo.common.apiresult.ApiResultCode;
import com.example.woodo.common.exception.ConflictException;

public class BookConflictException extends ConflictException {
    public BookConflictException() {
        super(ApiResultCode.ALREADY_REGISTERED_BOOK.getMessage());
    }

    public BookConflictException(String message) {
        super(message);
    }

    @Override
    public ApiResultCode getResultCode() {
        return ApiResultCode.ALREADY_REGISTERED_BOOK;
    }
}
