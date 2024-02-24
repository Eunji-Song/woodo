package com.example.woodo.common.jwt;

import com.example.woodo.common.exception.user.UserNotFoundException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("[JwtAuthenticationFilter:doFilterInternal] 입력 토큰 검증");
        String accessToken = jwtUtil.resolveToken(request);

        if (accessToken != null && jwtUtil.validateToken(accessToken)) {
            // 토큰으로부터 유저 정보를 받아
            Authentication authentication = jwtUtil.getAuthentication(accessToken);
            log.info("authentication : {}", authentication.toString());
            // SecurityContext 에 객체 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}
