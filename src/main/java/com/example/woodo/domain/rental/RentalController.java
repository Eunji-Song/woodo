package com.example.woodo.domain.rental;

import com.example.woodo.common.apiresult.ApiResult;
import com.example.woodo.common.exception.InvalidDataException;
import com.example.woodo.domain.rental.dto.RentalRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rental")
@RequiredArgsConstructor
@Slf4j
public class RentalController {
    private final RentalService rentalService;

    // 도서 대여
    @PostMapping("")
    public ApiResult rental(@RequestBody RentalRequestDto rentalRequestDto) {
        // 도서 id 입력 여부 확인
        if (ObjectUtils.isEmpty(rentalRequestDto.getBooksId())) {
            throw new InvalidDataException("도서를 선택해주세요.");
        }

        rentalService.rental(rentalRequestDto);
        return ApiResult.success();
    }
}
