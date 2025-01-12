package com.hongphat.borrowservice.command.event;

import lombok.*;

import java.time.LocalDateTime;

/**
 * CreateBorrowEvent
 *
 * @author hongp
 * @description Happy Coding With Phat 😊😊
 * @since 6:41 CH 12/01/2025
 */
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateBorrowEvent {
	private String id;
	private String bookId;
	private String employeeId;
	private LocalDateTime borrowDate;
}
