package com.hongphat.common_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * The type Error message.
 *
 * @author hongp
 * @createDay 07 /01/2025
 * @description Happy Coding With Phat 😊😊
 */
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessage {
	private String code;
	private String message;
	private String details;
	private LocalDateTime timestamp;
	private String requestId;
}
