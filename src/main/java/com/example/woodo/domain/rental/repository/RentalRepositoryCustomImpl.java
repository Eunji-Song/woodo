package com.example.woodo.domain.rental.repository;

import com.example.woodo.domain.rental.QRentalLog;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@RequiredArgsConstructor
@Slf4j
public class RentalRepositoryCustomImpl implements RentalRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    /**
     * 자동 반납 도서 기준
     * - 대여 완료 시점으로부터 10초 이상 지난 데이터
     * - 반납 미 완료 처리 (return_date is null)
     */
    @Override
    public void bookReturn() {
        QRentalLog rentalLog = new QRentalLog("l");

        // ChronoUnit을 이용하여 밀리초, 마이크로초 제거
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        LocalDateTime beforeSec = now.minusSeconds(10);

        log.info("삭제 데이터 생성 시간 기준 : {}", beforeSec);

        queryFactory.update(rentalLog)
                .set(rentalLog.returnDate, now)
                .where(
                    rentalLog.returnDate.isNull()
                    .and(rentalLog.rentalDate.loe(beforeSec))
                )
                .execute();


    }
}
