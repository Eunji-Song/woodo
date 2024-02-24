package com.example.woodo.common.apiresult;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ApiResultCode {
    // common
    SUCCESS(200, "SUCCESS"),
    ERROR(500, "ERROR"),
    BAD_REQUEST(400,  "올바른 요청 데이터 형식이 아닙니다. 요청 데이터를 확인하고 다시 시도해 주세요."),
    NOT_FOUND(404,  "데이터를 찾을 수 없습니다."),
    CONFLICT(409,  "이미 등록된 데이터입니다."),

    // Auth
    UNAUTHORIZED_USER(401, "인증되지 않은 사용자입니다."), // HttpStatus.UNAUTHORIZED
    EXPIRED_TOKEN(401, "유효하지 않은 토큰입니다.");  // 토큰 만료 or 유효하지 않은 토큰

    // by domain

    private final int code;
    private final String message;

    ApiResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
