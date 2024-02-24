package com.example.woodo.common.exception.user;

import com.example.woodo.common.exception.NotFoundException;
import org.hibernate.annotations.NotFound;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException() {
        super("사용자를 찾을 수 업습니다.");
    }
}
