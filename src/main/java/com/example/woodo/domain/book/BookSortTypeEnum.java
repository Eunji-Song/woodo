package com.example.woodo.domain.book;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BookSortTypeEnum {
    RENTAL_PRICE("rentalPrice"),
    RENTAL_COUNT("rentalCount"),
    CREATED_AT("createdAt");

    private final String type;
}
