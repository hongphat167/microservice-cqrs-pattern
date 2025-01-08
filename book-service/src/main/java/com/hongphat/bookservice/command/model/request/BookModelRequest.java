package com.hongphat.bookservice.command.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;


/**
 * The type Book request model.
 *
 * @author hongp
 * @createDay 05 /01/2025
 * @description Happy Coding With Phat 😊😊
 */
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class BookModelRequest {
	private String id;
	@NotBlank(message = "name can not be blank")
	@Size(min = 2, max = 30, message = "Name must be between 2 and 30")
	private String name;
	@NotBlank(message = "author can not be blank")
	private String author;
	private Boolean isReady;
}
