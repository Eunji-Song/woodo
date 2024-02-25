package com.example.woodo.domain.book.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class BookDto {
    private Long id;
    private String title;
    private String isbn;
    private int rentalPrice;
    private String consignorName;

    public BookDto() {
    }

    @QueryProjection
    public BookDto(Long id, String title, String isbn, int rentalPrice, String consignorName) {
        this.id = id;
        this.title = title;
        this.isbn = isbn;
        this.rentalPrice = rentalPrice;
        this.consignorName = consignorName;
    }
}
