package com.example.woodo.domain.rental;

import com.example.woodo.common.apiresult.ApiResult;
import com.example.woodo.common.exception.InvalidDataException;
import com.example.woodo.domain.rental.dto.RentalRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "도서 대여", description = "")
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "필수 값 미입력 / 형식이 올바르지 않은 갑 입력"),
            @ApiResponse(responseCode = "401", description = "만료된 토큰 입력"),
            @ApiResponse(responseCode = "409", description = "대여 불가한 도서 선택"),
        }
    )
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
