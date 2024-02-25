package com.example.woodo.domain.book;

import com.example.woodo.common.entity.BaseEntity;
import com.example.woodo.domain.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Table(name = "book")
@ToString
public class Book extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "rental_price")
    @ColumnDefault("0")
    private int rentalPrice;

    // 대여 횟수 (로그 데이터가 많아지는 경우 조인 시 조회 속도가 느려질 수 있으므로 컬럼으로 관리)
    @Column(name = "rental_count")
    @ColumnDefault("0")
    private int rentalCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Book() {
    }

    @Builder(toBuilder = true)
    public Book(Long id, String title, String isbn, int rentalPrice, User user) {
        this.id = id;
        this.title = title;
        this.isbn = isbn;
        this.rentalPrice = rentalPrice;
        this.user = user;
    }

}
