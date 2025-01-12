package com.hongphat.borrowservice.module.entity;

import com.hongphat.common_service.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * BorrowEntity
 *
 * @author hongp
 * @description Happy Coding With Phat 😊😊
 * @since 1 :43 CH 12/01/2025
 */
@Entity
@Table(name = "BORROW")
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class BorrowEntity extends BaseEntity {

	@Column(name = "BOOK_ID")
	private String bookId;

	@Column(name = "EMPLOYEE_ID")
	private String employeeId;

	@Column(name = "BORROW_DATE")
	private LocalDateTime borrowDate;

	@Column(name = "RETURN_DATE")
	private LocalDateTime returnDate;
}
