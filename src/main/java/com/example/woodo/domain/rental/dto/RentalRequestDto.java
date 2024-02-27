package com.example.woodo.domain.rental.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class RentalRequestDto {
    @Schema(description = "도서id")
    @NotEmpty(message = "도서를 선택해주세요.")
    private Long[] booksId;

    public RentalRequestDto() {
    }

    public RentalRequestDto(Long[] booksId) {
        this.booksId = booksId;
    }
}
