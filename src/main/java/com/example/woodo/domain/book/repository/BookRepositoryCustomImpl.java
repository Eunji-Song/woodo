package com.example.woodo.domain.book.repository;

import com.example.woodo.domain.book.QBook;
import com.example.woodo.domain.book.dto.BookDto;
import com.example.woodo.domain.book.dto.QBookDto;
import com.example.woodo.domain.rental.QRentalLog;
import com.example.woodo.domain.user.QUser;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.SortDirection;
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


        // Total Count Query
        Long totalCount = queryFactory
                .select(book.count())
                .from(book)
                .fetchOne();




        // 정렬 생성
        OrderSpecifier<?> orderSpecifier = getOrderSpecifier(pageable, book);


        // list 조회 Query
        List<BookDto> list = queryFactory
                .select(new QBookDto(book.id, book.title, book.isbn, book.rentalPrice, user.username))
                .from(book)
                .innerJoin(book.user, user)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(orderSpecifier)
                .fetch();




        return new PageImpl<>(list, pageable, totalCount);
    }

    private OrderSpecifier<?> getOrderSpecifier(Pageable pageable, QBook book) {
        String orderTarget = pageable.getSort().toList().get(0).getProperty();
        Sort.Direction dir = pageable.getSort().toList().get(0).getDirection();

        if (!pageable.getSort().isEmpty()) {
            switch (orderTarget) {
                case "rentalPrice" :
                    return dir == Sort.Direction.ASC ? book.rentalPrice.asc() : book.rentalPrice.desc();
                case "rentalCount" :
                    return dir == Sort.Direction.ASC ? book.rentalCount.asc() : book.rentalCount.desc();
                case "createdAt" :
                    return dir == Sort.Direction.ASC ? book.createdAt.asc() : book.createdAt.desc();
                default:
                    return book.id.desc();
            }
        }
        return book.id.desc();

    }

}
