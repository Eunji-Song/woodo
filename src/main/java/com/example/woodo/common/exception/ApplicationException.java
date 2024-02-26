package com.example.woodo.common.exception;

import com.example.woodo.common.apiresult.ApiResultCode;
import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {
    private ApiResultCode resultCode;
    public ApplicationException(ApiResultCode ex) {
        super(ex.getMessage());
        this.resultCode = ex;
    }

    public ApplicationException(ApiResultCode ex, String message) {
        super(message);
        this.resultCode = ex;
    }


}
