package com.hongphat.borrowservice.command.model.request;

import lombok.*;

import java.time.LocalDateTime;

/**
 * BorrowRequest
 *
 * @author hongp
 * @description Happy Coding With Phat 😊😊
 * @since 8:20 CH 12/01/2025
 */
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class BorrowRequest {
	private String bookId;
	private String employeeId;
}
