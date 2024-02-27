package com.example.woodo.domain.user.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserJoinRequestDto {
    // 이름
    @Schema(description = "이름")
    @NotBlank(message = "이름을 입력해 주세요.")
    private String username;

    // 이메일
    @Schema(description = "이메일")
    @NotBlank(message = "이메일을 입력해 주세요.")
    @Email(message = "이메일 형식에 올바르지 않은 값이 입력되었습니다.")
    private String email;

    // 휴대폰 번호
    @Schema(description = "휴대폰 번호 : 010-0000-0000")
    @NotBlank(message = "휴대폰 번호를 입력해 주세요.")
    @Pattern(regexp = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$", message = "휴대폰 번호 형식이 올바르지 않습니다.")
    private String phoneNumber;

    // 비밀번호
    @Schema(description = "비밀번호 : 최소 6자 이상 10자 이하, 최소 6자 이상 10자 이하")
    @NotBlank(message = "비밀번호를 입력해 주세요.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9]).{6,10}$", message = "비밀번호는 6~10자 영문 대/소문자, 숫자 중 최소 두 가지 이상 사용해주세요.")
    private String password;

    public void setPassword(String password) {
        this.password = password;
    }
}
