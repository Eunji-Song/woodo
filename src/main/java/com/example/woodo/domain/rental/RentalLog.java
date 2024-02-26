package com.example.woodo.domain.rental;

import com.example.woodo.common.entity.BaseEntity;
import com.example.woodo.domain.book.Book;
import com.example.woodo.domain.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 도서 대여 로그 엔티티
 */
@Entity
@Table(name = "rental_log")
@Getter
public class RentalLog extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    private LocalDateTime rentalDate;

    private LocalDateTime returnDate;

    public RentalLog() {
    }

    @Builder
    public RentalLog(User user, Book book, LocalDateTime rentalDate, LocalDateTime returnDate) {
        this.user = user;
        this.book = book;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
    }
}
