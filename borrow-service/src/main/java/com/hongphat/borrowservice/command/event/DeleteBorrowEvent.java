package com.hongphat.borrowservice.command.event;

import lombok.*;

/**
 * DeleteBorrowEvent
 *
 * @author hongp
 * @description Happy Coding With Phat 😊😊
 * @since 10:39 CH 14/01/2025
 */
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class DeleteBorrowEvent {
	private String id;
}
