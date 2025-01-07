package com.hongphat.bookservice.command.model;

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
public class BookModel {
	private String id;
	private String name;
	private String author;
	private Boolean isReady;
}
