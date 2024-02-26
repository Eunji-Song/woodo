package com.example.woodo.domain.rental.repository;

import com.example.woodo.domain.rental.RentalLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRepository extends JpaRepository<RentalLog, Long>, RentalRepositoryCustom {
    RentalLog findFirstByBookIdOrderByIdDesc(Long book_id);
}
