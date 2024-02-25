package com.example.woodo.domain.book;

import com.example.woodo.common.exception.InvalidDataException;
import com.example.woodo.common.exception.book.BookConflictException;
import com.example.woodo.common.jwt.JWTUtil;
import com.example.woodo.domain.book.dto.BookConsignmentRequestDto;
import com.example.woodo.domain.book.dto.BookDto;
import com.example.woodo.domain.book.dto.BookListDto;
import com.example.woodo.domain.book.repository.BookRepository;
import com.example.woodo.domain.book.repository.BookRepositoryCustomImpl;
import com.example.woodo.domain.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;


    /**
     * 위탁 도서 목록 조회
     * - 노출 도서 갯수 : 20
     * - 필터링 옵션 : 대여 많은 순, 낮은 가격순, 최근 등록일
     * @param pageable
     */
    public BookListDto findAllBooks(Pageable pageable) {
        Page<BookDto> list = bookRepository.findAllBooks(pageable);
        BookListDto bookListDto = BookListDto.builder().list(list.getContent()).totalCount(list.getTotalElements()).build();
        return bookListDto;
    }


    private boolean isValidBook(BookConsignmentRequestDto bookConsignmentRequestDto) {
        // ISBN 길이 검증 (ISBN-10, ISBN-13)
        String isbn = bookConsignmentRequestDto.getIsbn();
        if (isbn.length() < 10 || isbn.length() > 17) {
            throw new InvalidDataException("유효하지 않은 ISBN값이 입력되었습니다.");
        }

        // 금액은 100원 단위로 입력 가능
        int rentalPrice = bookConsignmentRequestDto.getRentalPrice();
        if (rentalPrice % 100 > 0) {
            throw new InvalidDataException("대여 가격은 100원 단위로 입력해주세요.");
        }

        return true;
    }

    // 도서 위탁
    public void consignment(BookConsignmentRequestDto bookConsignmentRequestDto) {
        // 위탁 회원
        User userInfo = JWTUtil.getCurrentUserInfo();

        // ISBN 하이픈 제거
        String isbn = removeHyphensFromIsbn(bookConsignmentRequestDto.getIsbn());

        // DTO에 데이터 할당
        BookConsignmentRequestDto rebuildDto = bookConsignmentRequestDto.toBuilder()
                .user(userInfo)
                .isbn(isbn)
                .build();

        // 데이터 유효성 검사
        isValidBook(rebuildDto);

        // 도서 ISBN 중복 검사
        boolean isExistBook = bookRepository.existsByIsbn(isbn);
        if (isExistBook) {
            throw new BookConflictException(isbn);
        }

        // DTO -> Entity 변환
        Book book = bookMapper.consignmentRequestDtoToEntity(rebuildDto);
        bookRepository.save(book);
    }

    // ISBN 하이픈 제거
    private String removeHyphensFromIsbn(String isbn) {
        return isbn.replaceAll("-", "");
    }
}
