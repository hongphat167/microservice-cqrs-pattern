package com.hongphat.borrowservice.command.event;

import lombok.*;

/**
 * UpdateBookStatusEvent
 *
 * @author hongp
 * @description Happy Coding With Phat 😊😊
 * @since 10 :35 CH 14/01/2025
 */
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateBookStatusEvent {
	private String bookId;
	private String employeeId;
	private Boolean isReady;
}
