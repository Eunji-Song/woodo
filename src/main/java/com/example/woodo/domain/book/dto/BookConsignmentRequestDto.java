package com.example.woodo.domain.book.dto;

import com.example.woodo.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@ToString
public class BookConsignmentRequestDto {
    // 도서명
    @Schema(description = "도서 제목")
    @NotBlank(message = "도서 제목을 입력해 주세요.")
    private String title;

    // ISBN
    @Schema(description = "ISBN코드, ISBN 중복 불가 및 최소 10글자 이상/하이픈 입력한 경우 17글자 이하 입력 가능")
    @NotBlank(message = "ISBN 코드를 입력해 주세요.")
    private String isbn;

    // 대여 가격
    @Schema(description = "대여 가격, 100원 단위로 입력 가능")
    @NotNull(message = "가격을 입력해 주세요.")
    @DecimalMin(value = "0", inclusive = true, message = "정수 값만 입력해 주세요.")
    private int rentalPrice;

    @JsonIgnore
    private User user;

    @Builder(toBuilder = true)
    public BookConsignmentRequestDto(String title, String isbn, int rentalPrice, User user) {
        this.title = title;
        this.isbn = isbn;
        this.rentalPrice = rentalPrice;
        this.user = user;
    }


}
