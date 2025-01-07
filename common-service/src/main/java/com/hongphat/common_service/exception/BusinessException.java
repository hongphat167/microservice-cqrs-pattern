package com.hongphat.common_service.exception;

import com.hongphat.common_service.enumerate.ErrorCode;
import lombok.Getter;

/**
 * @author hongp
 * @createDay 08/01/2025
 * @description Happy Coding With Phat 😊😊
 */
@Getter
public class BusinessException extends RuntimeException {
	private final ErrorCode errorCode;
	private final String details;

	public BusinessException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
		this.details = errorCode.getMessage();
	}

	public BusinessException(ErrorCode errorCode, String details) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
		this.details = details;
	}

	public BusinessException(ErrorCode errorCode, String details, Throwable cause) {
		super(errorCode.getMessage(), cause);
		this.errorCode = errorCode;
		this.details = details;
	}
}
