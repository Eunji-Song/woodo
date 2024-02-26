package com.example.woodo.domain.rental;

import com.example.woodo.common.exception.book.BookNotfoundException;
import com.example.woodo.common.exception.rental.BookAlreadyRentedException;
import com.example.woodo.common.jwt.JWTUtil;
import com.example.woodo.domain.book.Book;
import com.example.woodo.domain.book.repository.BookRepository;
import com.example.woodo.domain.rental.dto.RentalRequestDto;
import com.example.woodo.domain.rental.repository.RentalRepository;
import com.example.woodo.domain.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class RentalService {
    private final RentalRepository rentalRepository;
    private final BookRepository bookRepository;

    /**
     * 도서 대여하기
     * - 도서 다중 선택 가능
     * - 선택한 도서 중 마지막 대여 로그 확인
     */
    @Transactional
    public void rental(RentalRequestDto rentalRequestDto) {
        log.info("dto  : {}" , rentalRequestDto.toString() );

        // 사용자 정보
        User user = JWTUtil.getCurrentUserInfo();

        /* 대여 로그 생성 */

        // 로그 리스트 초기화
        List<RentalLog> logs = new ArrayList<>();

        // 선택한 도서 대여 가능 여부 확인 및 로그 생성
        Long[] selectedBooksId = rentalRequestDto.getBooksId();
        if (rentalRequestDto.getBooksId().length > 0) {
            for (Long bookId : selectedBooksId) {
                // 도서 유효 여부 확인
                Book book = bookRepository.findById(bookId).orElseThrow(() -> new BookNotfoundException("유효하지 않은 도서가 선택되었습니다."));

                // 도서의 마지막 로그 호출
                RentalLog lastLog = rentalRepository.findFirstByBookIdOrderByIdDesc(bookId);

                // 로그가 존재하는 경우 유효성 검사 진행
                if (!ObjectUtils.isEmpty(lastLog)) {
                    // 반납 일자가 null인 경우 대여 불가
                    if (lastLog.getReturnDate() == null) {
                        throw new BookAlreadyRentedException(book.getTitle());
                    }
                }

                RentalLog rentalLog = RentalLog.builder()
                        .user(user)
                        .book(book)
                        .build();

                logs.add(rentalLog);
            }
        }

         rentalRepository.saveAll(logs);
    }


    /**
     * 도서 반납
     * - 10분 간격으로 스케줄러 실행
     */
    @Scheduled(fixedDelay = 600000)
    public void bookReturn() {
        rentalRepository.bookReturn();
    }
}
