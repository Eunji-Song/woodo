package com.example.woodo.domain.book.dto;

import com.example.woodo.domain.user.User;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@ToString
public class BookConsignmentRequestDto {
    // 도서명
    @NotBlank(message = "도서 제목을 입력해 주세요.")
    private String title;

    // ISBN
    @NotBlank(message = "ISBN 코드를 입력해 주세요.")
    private String isbn;

    // 대여 가격
    @NotNull(message = "가격을 입력해 주세요.")
    @DecimalMin(value = "0", inclusive = true, message = "정수 값만 입력해 주세요.")
    private int rentalPrice;

    private User user;

    @Builder(toBuilder = true)
    public BookConsignmentRequestDto(String title, String isbn, int rentalPrice, User user) {
        this.title = title;
        this.isbn = isbn;
        this.rentalPrice = rentalPrice;
        this.user = user;
    }


}
