package com.example.woodo.common.exception.user;

import com.example.woodo.common.apiresult.ApiResultCode;
import com.example.woodo.common.exception.NotFoundException;
import org.hibernate.annotations.NotFound;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException() {
        super(ApiResultCode.UNAUTHORIZED_USER.getMessage());
    }

    public UserNotFoundException(String message) {
        super(message);
    }

    @Override
    public ApiResultCode getResultCode() {
        return ApiResultCode.UNAUTHORIZED_USER;
    }
}
