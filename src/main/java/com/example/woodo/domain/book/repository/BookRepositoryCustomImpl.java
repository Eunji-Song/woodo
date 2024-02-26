package com.example.woodo.domain.book.repository;

import com.example.woodo.domain.book.QBook;
import com.example.woodo.domain.book.dto.BookDto;
import com.example.woodo.domain.book.dto.QBookDto;
import com.example.woodo.domain.rental.QRentalLog;
import com.example.woodo.domain.user.QUser;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class BookRepositoryCustomImpl implements BookRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<BookDto> findAllBooks(Pageable pageable) {
        QBook book = new QBook("b");
        QUser user = new QUser("u");
        QRentalLog rentalLog = new QRentalLog("l");





        // 정렬 생성
        OrderSpecifier<?> orderSpecifier = getOrderSpecifier(pageable, book);

        // list 조회 Query
        List<BookDto> list = queryFactory
                .select(
                    new QBookDto(
                            book.id, book.title, book.isbn, book.rentalPrice, user.username
                            // 대여 횟수 서브 쿼리 생성
                            , ExpressionUtils.as(
                                    JPAExpressions
                                            .select(rentalLog.count().intValue())
                                            .from(rentalLog)
                                            .where(rentalLog.book.id.eq(book.id))
                                , "rentalCount"
                            )
                    )

                )
                .from(book)
                .innerJoin(book.user, user)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(orderSpecifier)
                .fetch();



        // Total Count Query
        Long totalCount = queryFactory
                .select(book.count())
                .from(book)
                .fetchOne();

        return new PageImpl<>(list, pageable, totalCount);
    }

    // TODO : 다중 정렬
    private OrderSpecifier<?> getOrderSpecifier(Pageable pageable, QBook book) {
        Sort.Order firstOrder = pageable.getSort().toList().get(0);
        String orderTarget = firstOrder.getProperty();
        Sort.Direction direction = firstOrder.getDirection();


        if (!pageable.getSort().isEmpty()) {
            switch (orderTarget) {
                case "rentalPrice" :
                    return direction == Sort.Direction.ASC ? book.rentalPrice.asc() : book.rentalPrice.desc();
                case "rentalCount" :
                    NumberPath<Long> aliasCount = Expressions.numberPath(Long.class, "rentalCount");
                    return direction == Sort.Direction.ASC ? aliasCount.asc() : aliasCount.desc();
                case "createdAt" :
                    return direction == Sort.Direction.ASC ? book.createdAt.asc() : book.createdAt.desc();
                default:
                    return book.id.desc();
            }
        }
        return book.id.desc();

    }

}
