package com.example.woodo.common.jwt;

import com.example.woodo.common.apiresult.ApiResult;
import com.example.woodo.common.apiresult.ApiResultCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
   @Override
   public void commence(HttpServletRequest request,
                        HttpServletResponse response,
                        AuthenticationException authException) throws IOException {

      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.setContentType("application/json;charset=UTF-8");

      ObjectMapper mapper = new ObjectMapper(); // 객체 -> JSON 문자열로 직렬화

      ApiResult result = ApiResult.error(ApiResultCode.EXPIRED_TOKEN);
      String msg = mapper.writeValueAsString(result);

      response.getWriter().write(msg);
   }


}
