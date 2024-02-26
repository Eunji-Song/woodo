package com.example.woodo.domain.book;

import com.example.woodo.domain.book.dto.BookConsignmentRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    Book consignmentRequestDtoToEntity(BookConsignmentRequestDto bookConsignmentRequestDto);
}
