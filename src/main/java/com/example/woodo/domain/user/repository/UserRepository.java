package com.example.woodo.domain.user.repository;

import com.example.woodo.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // 회원 존재 여부 확인
    boolean existsByEmail(String email);

    // 회원 정보 조회
    User findByEmail(String email);
}
