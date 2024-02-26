package com.example.woodo.domain.rental.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class RentalRequestDto {
    @NotEmpty(message = "도서를 선택해주세요.")
    private Long[] booksId;

    public RentalRequestDto() {
    }

    public RentalRequestDto(Long[] booksId) {
        this.booksId = booksId;
    }
}
