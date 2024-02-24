package com.example.woodo.domain.user;

import com.example.woodo.common.apiresult.ApiResult;
import com.example.woodo.domain.user.dto.UserJoinRequestDto;
import com.example.woodo.domain.user.dto.UserLoginRequestDto;
import com.example.woodo.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "User", description = "사용자 관련 기능 처리 컨트롤러")
public class UserController {
    private final UserService userService;

    // 회원가입
    @Operation(summary = "회원가입", description = "이메일 중복 불가")
    @PostMapping("/join")
    public ApiResult join(@RequestBody @Valid UserJoinRequestDto userJoinRequestDto) {
        boolean isSuccess = userService.join(userJoinRequestDto);
        if (!isSuccess) {
            return ApiResult.error();
        }
        return ApiResult.success();
    }

    // 로그인
    @Operation(summary = "로그인")
    @PostMapping("/login")
    public ApiResult login(@RequestBody @Valid UserLoginRequestDto userLoginRequestDto) {
        String accessToken = userService.login(userLoginRequestDto);
        return ApiResult.success(accessToken);
    }
}
