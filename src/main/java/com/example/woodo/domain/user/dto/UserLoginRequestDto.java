package com.example.woodo.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserLoginRequestDto {
    // 이메일
    @NotBlank(message = "이메일을 입력해 주세요.")
    @Email
    private String email;

    // 비밀번호
    @NotBlank(message = "비밀번호를 입력해 주세요.")
    private String password;
}
