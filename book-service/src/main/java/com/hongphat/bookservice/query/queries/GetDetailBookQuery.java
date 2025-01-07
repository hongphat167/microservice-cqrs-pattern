package com.hongphat.bookservice.query.queries;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * The type Get detail book query.
 *
 * @author hongp
 * @createDay 06 /01/2025
 * @description Happy Coding With Phat 😊😊
 */
@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
public class GetDetailBookQuery {
	private String bookId;
}
