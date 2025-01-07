package com.hongphat.bookservice.command.event;

import lombok.*;

/**
 * @author hongp
 * @createDay 05/01/2025
 * @description Happy Coding With Phat 😊😊
 */
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class BookDeleteEvent {
	private String id;
}
