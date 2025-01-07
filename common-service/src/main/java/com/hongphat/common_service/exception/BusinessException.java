package com.hongphat.common_service.exception;

import com.hongphat.common_service.enumerate.ErrorCode;
import lombok.Getter;

/**
 * The type Business exception.
 *
 * @author hongp
 * @createDay 08 /01/2025
 * @description Happy Coding With Phat 😊😊
 */
@Getter
public class BusinessException extends RuntimeException {
	private final ErrorCode errorCode;
	private final String details;

	/**
	 * Instantiates a new Business exception.
	 *
	 * @param errorCode the error code
	 */
	public BusinessException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
		this.details = errorCode.getMessage();
	}

	/**
	 * Instantiates a new Business exception.
	 *
	 * @param errorCode the error code
	 * @param details   the details
	 */
	public BusinessException(ErrorCode errorCode, String details) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
		this.details = details;
	}

	/**
	 * Instantiates a new Business exception.
	 *
	 * @param errorCode the error code
	 * @param details   the details
	 * @param cause     the cause
	 */
	public BusinessException(ErrorCode errorCode, String details, Throwable cause) {
		super(errorCode.getMessage(), cause);
		this.errorCode = errorCode;
		this.details = details;
	}
}
