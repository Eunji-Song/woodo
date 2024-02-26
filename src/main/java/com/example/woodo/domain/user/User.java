package com.example.woodo.domain.user;

import com.example.woodo.common.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@Table(name = "user")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "name")
    private String username;

    @Column(name = "email")
    @Email
    private String email;

    @Column(name = "phone_number")
    @Pattern(regexp = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$", message = "휴대폰 번호 형식이 올바르지 않습니다.")
    private String phoneNumber;

    /**
     * 비밀번호 조건
     * - 최소 6자 이상 10자 이하
     * - 영문 소문자, 대문자, 숫자 중 최소 두 가지 이상 조합 필요
     */
    @Column(name = "password")
    private String password;

    public User() {
    }

    @Builder(toBuilder = true)
    public User(String username, String email, String phoneNumber, String password) {
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public User(Long id) {
        this.id = id;
    }
}
