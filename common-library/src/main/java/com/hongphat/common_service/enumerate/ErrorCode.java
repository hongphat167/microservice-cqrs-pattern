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
	 * The Entity not found.
	 */
	ENTITY_NOT_FOUND("ERR_404", "Entity not found"),
	/**
	 * The Invalid credentials.
	 */
	INVALID_CREDENTIALS("ERR_401", "Invalid credentials"),
	/**
	 * The Data integrity violation.
	 */
	DATA_INTEGRITY_VIOLATION("ERR_409", "Data integrity violation"),
	/**
	 * The Invalid parameter.
	 */
	INVALID_PARAMETER("ERR_400_1", "Invalid parameter"),
	/**
	 * The Internal error.
	 */
	INTERNAL_ERROR("ERR_500", "Internal server error"),
	/**
	 * The Business error.
	 */
	BUSINESS_ERROR("ERR_400_2", "Business error"),

	/**
	 * The Book not available.
	 */
	BOOK_NOT_AVAILABLE("ERR_001", "Book is not available for borrowing");

	private final String code;
	private final String message;

	ErrorCode(String code, String message) {
		this.code = code;
		this.message = message;
	}
}
