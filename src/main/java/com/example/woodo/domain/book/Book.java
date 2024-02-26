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
