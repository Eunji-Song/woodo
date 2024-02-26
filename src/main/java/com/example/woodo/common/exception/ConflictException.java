package com.example.woodo.common.exception;

import com.example.woodo.common.apiresult.ApiResultCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
public class ConflictException extends RuntimeException {
    private ApiResultCode resultCode;
    private String message;

    public ConflictException() {
        super();
    }

    public ConflictException(String message) {
        super();
        this.resultCode = ApiResultCode.CONFLICT;
        this.message = message;
    }

}
