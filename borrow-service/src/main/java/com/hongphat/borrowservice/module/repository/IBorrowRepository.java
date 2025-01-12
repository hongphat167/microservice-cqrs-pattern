package com.hongphat.borrowservice.module.repository;

import com.hongphat.borrowservice.module.entity.BorrowEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * IBorrowRepository
 *
 * @author hongp
 * @description Happy Coding With Phat 😊😊
 * @since 1 :50 CH 12/01/2025
 */
@Repository
public interface IBorrowRepository extends JpaRepository<BorrowEntity, String> {
}
