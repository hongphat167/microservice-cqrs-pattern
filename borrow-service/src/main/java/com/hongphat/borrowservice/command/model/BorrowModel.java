package com.hongphat.borrowservice.command.model;

import lombok.*;

import java.time.LocalDateTime;

/**
 * BorrowModel
 *
 * @author hongp
 * @description Happy Coding With Phat 😊😊
 * @since 7:56 CH 12/01/2025
 */
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class BorrowModel {
	private String id;
	private String bookId;
	private String employeeId;
	private LocalDateTime borrowDate;
	private LocalDateTime returnDate;
}
