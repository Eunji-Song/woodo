package com.example.woodo.domain.user;

import com.example.woodo.domain.user.dto.UserJoinRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    // 회원가입
    User joinDtoToEntity(UserJoinRequestDto userJoinRequestDto);
}
