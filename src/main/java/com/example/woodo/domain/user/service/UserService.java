package com.example.woodo.domain.user.service;

import com.example.woodo.common.exception.user.UserConflictException;
import com.example.woodo.common.exception.user.UserNotFoundException;
import com.example.woodo.common.jwt.JWTUtil;
import com.example.woodo.domain.user.User;
import com.example.woodo.domain.user.UserMapper;
import com.example.woodo.domain.user.UserRepository;
import com.example.woodo.domain.user.dto.UserJoinRequestDto;
import com.example.woodo.domain.user.dto.UserLoginRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * controller - service 1:1 매핑되어 인터페이스로 생성하지는 않았습니다.
 */

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JWTUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    // 회원가입
    @Transactional
    public boolean join(UserJoinRequestDto userJoinRequestDto) {

        // 데이터 중복 조회
        boolean isExistEmail = userRepository.existsByEmail(userJoinRequestDto.getEmail());
        if (isExistEmail) {
            throw new UserConflictException(userJoinRequestDto.getEmail());
        }

        // 비밀번호 암호화
        String encodePassword = passwordEncoder.encode(userJoinRequestDto.getPassword());
        userJoinRequestDto.setPassword(encodePassword);

        // 데이터 저장
        User user = userMapper.joinDtoToEntity(userJoinRequestDto);
        userRepository.save(user);
        Long userId = user.getUserId();

        // 회원의 Id값 리턴
        return (userId != null);
    }

    // 로그인
    @Transactional
    public String login(UserLoginRequestDto userLoginRequestDto) {
        log.info("[UserService|login] 로그인 요청 실행 email : {}, password : {}", userLoginRequestDto.getEmail(), userLoginRequestDto.getPassword());
        String email = userLoginRequestDto.getEmail();
        String password = userLoginRequestDto.getPassword();

        User user = userRepository.findByEmail(email);
        if (user == null) {
            log.info("[UserService|login] findByEmail result is null");
            throw new UserNotFoundException();
        }

        // 비밀번호 확인
        if (!passwordEncoder.matches(password, user.getPassword())) {
            log.info("[UserService|login] Username and Password do not match.");
            throw new UserNotFoundException();
        }

        // Token 생성
        String accessToken = jwtUtil.createAccessToken(user);
        return accessToken;
    }


}
