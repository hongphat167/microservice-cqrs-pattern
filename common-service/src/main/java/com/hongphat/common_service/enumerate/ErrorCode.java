package com.hongphat.common_service.enumerate;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author hongp
 * @createDay 08/01/2025
 * @description Happy Coding With Phat 😊😊
 */
@Getter
public enum ErrorCode {
	VALIDATION_ERROR("400", "Validation failed", HttpStatus.BAD_REQUEST),
	ACCESS_DENIED("403", "Access denied", HttpStatus.FORBIDDEN),
	INTERNAL_ERROR("500", "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);

	private final String code;
	private final String message;
	private final HttpStatus status;

	ErrorCode(String code, String message, HttpStatus status) {
		this.code = code;
		this.message = message;
		this.status = status;
	}
}
