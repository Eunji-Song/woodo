package com.example.woodo.domain.book.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class BookListDto {
    private List<BookDto> list = new ArrayList<>();
    private Long totalCount;


    @Builder(toBuilder = true)
    public BookListDto(List<BookDto> list, Long totalCount) {
        this.list = list;
        this.totalCount = totalCount;
    }

    public BookListDto() {
    }
}
