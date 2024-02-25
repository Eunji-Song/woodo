package com.example.woodo.domain.book.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class BookListDto {
    private List<BookDto> list = new ArrayList<>();
    private Long totalPages;
    private Long totalCount;


    @Builder(toBuilder = true)
    public BookListDto(List<BookDto> list, Long totalPages, Long totalCount) {
        this.list = list;
        this.totalPages = totalPages;
        this.totalCount = totalCount;
    }

    public BookListDto() {
    }
}
