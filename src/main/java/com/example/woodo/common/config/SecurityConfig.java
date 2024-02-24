package com.example.woodo.common.config;

import com.example.woodo.common.jwt.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity // 스프링 시큐리티, 웹 보안 설정 구성 활성화, 인증이 완료되지 않은 사용자는 API에 접근 불가
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthenticationConfiguration authenticationConfiguration;
    private final JWTUtil jwtUtil;

    /**
     * filterChain
     * SecurityFilterChain => Spring Security에서 제공하는 인증, 인가를 위한 필터들의 모음
     * AbstractHttpConfigurer::disable => 기본값을 사용
     * authenticated => 인증 필수
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity security) throws Exception {
        security.httpBasic(AbstractHttpConfigurer::disable) // UI기반의 인증창이 뜨는것을 비활성화
                .formLogin(AbstractHttpConfigurer::disable) // form 로그인 기능 비활성화
                .csrf(AbstractHttpConfigurer::disable)
                // 인증 필수 관련 페이지 설정
                .authorizeHttpRequests(
                    requests -> requests
                            .requestMatchers("/users/join", "/users/login").permitAll()
                            .requestMatchers("/v3/**", "/swagger-ui/**").permitAll()
                            .requestMatchers("/test/**").permitAll()
                            .anyRequest().authenticated()
                )
                .sessionManagement(
                        (session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(
                        exception -> exception
                                // 접근 가능 권한 확인 후 접근 불가능한 요청을 했을 때 동작
                                .accessDeniedHandler(new JwtCustomAccessDeniedHandler())
                                // 인증되지 않은 유저가 요청했을 때
                                .authenticationEntryPoint(new JwtAuthenticationEntryPoint())
                );

        return security.build();
    }

    //AuthenticationManager Bean
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
