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


    // User
    EXPIRED_TOKEN(401, "유효하지 않은 토큰입니다."),  // 토큰 만료 or 유효하지 않은 토큰
    UNAUTHORIZED_USER(401, "아이디 또는 패스워드가 일치하지 않습니다."), // HttpStatus.UNAUTHORIZED
    ALREADY_JOINED_EMAIL(409, "이미 가입된 이메일 입니다."),

    // BOOK
    ALREADY_REGISTERED_BOOK(409, "이미 위탁이 완료된 도서입니다."),
    NOTFOUND_BOOK(404, "도서를 찾을 수 없습니다."),

    // Rental
    ALREADY_RENTED_BOOK(409, "대여가 불가능한 도서가 포함되어 있습니다.");

    private final int code;
    private final String message;

    ApiResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
