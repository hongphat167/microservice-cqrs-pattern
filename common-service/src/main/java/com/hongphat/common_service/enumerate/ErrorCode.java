package com.hongphat.common_service.enumerate;

import lombok.Getter;

/**
 * The enum Error code.
 *
 * @author hongp
 * @createDay 08 /01/2025
 * @description Happy Coding With Phat 😊😊
 */
@Getter
public enum ErrorCode {
	/**
	 * The Validation error.
	 */
	VALIDATION_ERROR("ERR_400", "Validation failed"),
	/**
	 * The Access denied.
	 */
	ACCESS_DENIED("ERR_403", "Access denied"),
	/**
	 * The Internal error.
	 */
	INTERNAL_ERROR("ERR_500", "System is maintain"),
	/**
	 * The Business error.
	 */
	BUSINESS_ERROR("ERR_001", "Business error");

	private final String code;
	private final String message;

	ErrorCode(String code, String message) {
		this.code = code;
		this.message = message;
	}
}
