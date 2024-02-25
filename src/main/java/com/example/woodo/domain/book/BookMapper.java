package com.example.woodo.domain.book;

import com.example.woodo.domain.book.dto.BookConsignmentRequestDto;
import com.example.woodo.domain.user.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.web.bind.annotation.Mapping;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    Book consignmentRequestDtoToEntity(BookConsignmentRequestDto bookConsignmentRequestDto);
}
