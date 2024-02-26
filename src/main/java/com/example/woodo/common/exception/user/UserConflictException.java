package com.example.woodo.common.exception.user;

import com.example.woodo.common.apiresult.ApiResultCode;
import com.example.woodo.common.exception.ConflictException;

public class UserConflictException extends ConflictException {

    public UserConflictException() {
        super(ApiResultCode.ALREADY_JOINED_EMAIL.getMessage());
    }

    public UserConflictException(String message) {
        super(message);
    }

    @Override
    public ApiResultCode getResultCode() {
        return ApiResultCode.ALREADY_JOINED_EMAIL;
    }
}
