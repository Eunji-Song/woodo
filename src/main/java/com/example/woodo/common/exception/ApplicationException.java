package com.example.woodo.common.exception;

import com.example.woodo.common.apiresult.ApiResultCode;
import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {
    private ApiResultCode resultCode;
    private String message;

    public ApplicationException() {
        super();
    }

    public ApplicationException(String message) {
        this.message = message;
    }
}
