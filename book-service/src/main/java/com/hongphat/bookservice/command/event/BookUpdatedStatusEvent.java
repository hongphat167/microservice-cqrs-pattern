package com.hongphat.bookservice.command.event;

import lombok.*;

/**
 * BookUpdatedStatusEvent
 *
 * @author hongp
 * @description Happy Coding With Phat 😊😊
 * @since 7:10 CH 17/01/2025
 */
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class BookUpdatedStatusEvent {
	private String id;
	private Boolean isReady;
}
