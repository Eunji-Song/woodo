package com.example.woodo.common.jwt;

import ch.qos.logback.core.net.server.Client;
import com.example.woodo.common.exception.ConflictException;
import com.example.woodo.common.exception.NotFoundException;
import com.example.woodo.common.exception.user.UserNotFoundException;
import com.example.woodo.domain.user.User;
import com.example.woodo.domain.user.dto.CustomUserDetails;
import com.example.woodo.domain.user.service.CustomUserDetailsService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.PublicKey;
import java.util.Date;

@Component
@Slf4j
public class JWTUtil {
    private final Key key;
    private final Long tokenExpMs;
    public static final String AUTHORIZATION_HEADER = "Authorization";
    private final CustomUserDetailsService customUserDetailsService;

    public JWTUtil(@Value("${spring.jwt.secret-key}") String secretKey
                    , @Value("${spring.jwt.expiration-period-ms}") Long tokenExpMs
    , CustomUserDetailsService customUserDetailsService) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.tokenExpMs = tokenExpMs;
        this.customUserDetailsService = customUserDetailsService;
    }

    // Access Token 생성하기
    public String createAccessToken(User user) {
        return createToken(user);
    }

    // Token 생성
    private String createToken(User user) {
        Claims claims = Jwts.claims();
        claims.put("email", user.getEmail());
        claims.put("userId", user.getId());

        // 토큰 만료 기간 생성
        long now = (new Date()).getTime();
        Date validity = new Date(now + tokenExpMs);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(validity)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // 토큰 만료여부 검사
    public boolean validateToken(String accessToken) {
        log.info("[validateToken] token : {}", accessToken);
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("[validateToken] Invalid Token");
        } catch (ExpiredJwtException e) {
            log.info("[validateToken] Expired Token");
        } catch (UnsupportedJwtException e) {
            log.info("[validateToken] Unsupported Token");
        } catch (IllegalArgumentException e) {
            log.info("[validateToken] JWT Claims is Empty");
        }
        return false;
    }

    // Token에서 user id 값 추출하기
    public Long getUserId(String accessToken) {
        return parseClaimsData(accessToken).get("userId", Long.class);
    }

    // JWT Claims 데이터 추출
    public Claims parseClaimsData(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            log.info("토큰이 만료되었습니다.");
            return e.getClaims();
        }
    }

    // 사용자 정보 인증
    public Authentication getAuthentication(String token) {
        log.info("getAutentiation  token : {}", token);
        try {
            Claims claims = Jwts
                    .parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String email = claims.get("email", String.class);
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
            return new UsernamePasswordAuthenticationToken(userDetails, "",null);
        } catch (Exception e) {
            throw new UserNotFoundException();
        }
    }

    // Request Header에서 토큰 정보 추출
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            log.info("Token : {}", bearerToken.substring(7));
            return bearerToken.substring(7);
        }

        return null;
    }


    // 사용자 id 리턴
    public static Long getCurrentUserId() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new RuntimeException(" Security Context에 인증 정보가 없습니다.");
        }

        Object principal = authentication.getPrincipal();
        if (!(principal instanceof CustomUserDetails)) {
            throw new UserNotFoundException();
        }

        Long userId = ((CustomUserDetails) authentication.getPrincipal()).getUserId();;

        return userId;
    }

    // 사용자 정보 리턴
    public static User getCurrentUserInfo() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new RuntimeException(" Security Context에 인증 정보가 없습니다.");
        }

        Object principal = authentication.getPrincipal();
        if (!(principal instanceof CustomUserDetails)) {
            throw new UserNotFoundException();
        }


        CustomUserDetails userDetails = (CustomUserDetails) principal;

        User userInfo = new User(userDetails.getUserId());
        return userInfo;

    }



}
