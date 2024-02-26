package com.example.woodo.common.exception;


import com.example.woodo.common.apiresult.ApiResultCode;
import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException{
    private ApiResultCode resultCode;
    private String message;

    public NotFoundException() {
        super();
    }

    public NotFoundException(String message) {
        super(message);
        this.resultCode = ApiResultCode.NOT_FOUND;
        this.message = message;
    }
}
