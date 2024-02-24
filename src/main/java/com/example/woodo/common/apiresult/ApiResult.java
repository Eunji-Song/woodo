package com.example.woodo.common.apiresult;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResult<T> {
    private final Integer code; // ResultCode.code
    private final String message; // ResultCode.message or custom message
    private final T data; // return data


    @Builder
    public ApiResult(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static ApiResult success(){
        return ApiResult.builder()
                .code(ApiResultCode.SUCCESS.getCode())
                .message(ApiResultCode.SUCCESS.getMessage())
                .build();
    }

    public static <T> ApiResult success(T data){
        return new ApiResult(ApiResultCode.SUCCESS.getCode(), ApiResultCode.SUCCESS.getMessage(), data);
    }

    public static ApiResult error() {
        return ApiResult.builder()
                .code(ApiResultCode.ERROR.getCode())
                .message(ApiResultCode.ERROR.getMessage())
                .build();
    }

    // 응답코드 입력
    public static ApiResult error(ApiResultCode apiResultCode) {
        return ApiResult.builder()
                .code(apiResultCode.getCode())
                .message(apiResultCode.getMessage())
                .build();
    }


    // 응답코드, 메시지 입력
    public static ApiResult error(ApiResultCode apiResultCode, String message) {
        return ApiResult.builder()
                .code(apiResultCode.getCode())
                .message(message)
                .build();
    }


}
