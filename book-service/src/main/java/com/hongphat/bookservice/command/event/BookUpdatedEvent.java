package com.hongphat.bookservice.command.event;

import lombok.*;

/**
 * The type Book updated event.
 *
 * @author hongp
 * @createDay 05 /01/2025
 * @description Happy Coding With Phat 😊😊
 */
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class BookUpdatedEvent {
	private String id;
	private String name;
	private String author;
	private Boolean isReady;
}
