package com.example.woodo.domain.user;

import com.example.woodo.common.apiresult.ApiResult;
import com.example.woodo.common.exception.InvalidDataException;
import com.example.woodo.domain.user.dto.UserJoinRequestDto;
import com.example.woodo.domain.user.dto.UserLoginRequestDto;
import com.example.woodo.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "필수 값 미입력 / 형식이 올바르지 않은 갑 입력"),
            @ApiResponse(responseCode = "409", description = "이메일 중복"),
        }
    )
    @PostMapping("/join")
    public ApiResult join(@RequestBody @Valid UserJoinRequestDto userJoinRequestDto) {
        userService.join(userJoinRequestDto);
        return ApiResult.success();
    }

    // 로그인
    @Operation(summary = "로그인")
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "필수 값 미입력 / 형식이 올바르지 않은 갑 입력"),
            @ApiResponse(responseCode = "401", description = "아이디,패스워드 불일치"),
        }
    )
    @PostMapping("/login")
    public ApiResult login(@RequestBody @Valid UserLoginRequestDto userLoginRequestDto) {
        String accessToken = userService.login(userLoginRequestDto);
        return ApiResult.success(accessToken);
    }
}
