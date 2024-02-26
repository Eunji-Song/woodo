package com.example.woodo.domain.book;

import com.example.woodo.common.apiresult.ApiResult;
import com.example.woodo.domain.book.dto.BookConsignmentRequestDto;
import com.example.woodo.domain.book.dto.BookListDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
@Tag(name = "Book", description = "도서 관련 기능 처리 컨트롤러")
public class BookController {
    private final BookService bookService;

    // 대여하기 > 도서 목록
    @Operation(summary = "도서 목록 조회", description = "")
    @GetMapping("")
    public ApiResult findAllBooks(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "20") int size,
                                  @RequestParam(defaultValue = "price") BookSortTypeEnum sortBy,
                                  @RequestParam(defaultValue = "asc") String sortDirection) {
        // 정렬 데이터를 담은 객체 생성
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy.getType());

        // 페이징 처리를 위한 객체 생성
        Pageable pageable = PageRequest.of(page, size, sort);

        BookListDto listDto = bookService.findAllBooks(pageable);

        return ApiResult.success(listDto);
    }



    // 도서 위탁하기
    @Operation(summary = "도서 위탁", description = "유효성 검사 조건 \n\n - ISBN 값 중복 불가\n\n - ISBN 길이 최소 10글자 이상/하이픈 입력한 경우 17글자 이하")
    @PostMapping("/consignment")
    public ApiResult consignment(@RequestBody @Valid BookConsignmentRequestDto bookConsignmentRequestDto) {
        bookService.consignment(bookConsignmentRequestDto);
        return ApiResult.success();
    }

}
