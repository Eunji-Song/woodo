package com.example.woodo.domain.book;

import com.example.woodo.common.apiresult.ApiResult;
import com.example.woodo.domain.book.dto.BookConsignmentRequestDto;
import com.example.woodo.domain.book.dto.BookListDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
@Tag(name = "Book", description = "도서 관련 기능 처리 컨트롤러")
public class BookController {
    private final BookService bookService;

    // 도서 목록
    @Operation(summary = "도서 목록 조회", description = "")
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "401", description = "만료된 토큰 입력"),
        }
    )
    @Parameters({
            @Parameter(name = "sortBy", description = "정렬 타입 : rentalPrice, rentalCount, createdAt")
    })
    @GetMapping("")
    public ApiResult findAllBooks(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "20") int size,
                                    @RequestParam(defaultValue = "rentalCount") String sortBy,
                                    @RequestParam(defaultValue = "desc") String sortDirection) {

        // 정렬 데이터를 담은 객체 생성
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);

        // 페이징 처리를 위한 객체 생성
        Pageable pageable = PageRequest.of(page, size, sort);

        BookListDto listDto = bookService.findAllBooks(pageable);

        return ApiResult.success(listDto);
    }


    // 도서 위탁
    @Operation(summary = "도서 위탁", description = "")
    @ApiResponses(
        value = {
                @ApiResponse(responseCode = "200", description = "성공"),
                @ApiResponse(responseCode = "400", description = "필수 값 미입력 / 형식이 올바르지 않은 갑 입력"),
                @ApiResponse(responseCode = "401", description = "유효하지 않은 토큰입니다."),
                @ApiResponse(responseCode = "409", description = "이미 위탁이 완료된 도서입니다.(ISBN 값 중복)")
        }
    )
    @PostMapping("/consignment")
    public ApiResult consignment(@RequestBody @Valid BookConsignmentRequestDto bookConsignmentRequestDto) {
        bookService.consignment(bookConsignmentRequestDto);
        return ApiResult.success();
    }

}
