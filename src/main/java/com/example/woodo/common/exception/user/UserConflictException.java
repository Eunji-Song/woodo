package com.example.woodo.common.exception.user;

import com.example.woodo.common.exception.ConflictException;

public class UserConflictException extends ConflictException {
    public UserConflictException(String email) {
        super(email + "은 이미 등록된 메일입니다.");
    }
}
