package com.example.woodo.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI api() {
        OpenAPI openAPI = new OpenAPI()
                // 보안 관련
                .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                .components(new Components().addSecuritySchemes("Bearer Authentication", createAPIKeyScheme()))
                .info(apiInfo());


        return openAPI;
    }

    // API 문서 정보
    private Info apiInfo() {
        Info info = new Info()
                .title("도서 위탁/대여 API 문서")
                .description("woodo 과제 전형 API 개발 문서 ")
                .version("0.0.1");

        return info;
    }


    // API KEY 입력을 위한 스키마 생성
    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP) // 스키마 유형 HTTP
                .bearerFormat("JWT") // 토큰 형식
                .scheme("bearer"); // 스키마 이름
    }





}
