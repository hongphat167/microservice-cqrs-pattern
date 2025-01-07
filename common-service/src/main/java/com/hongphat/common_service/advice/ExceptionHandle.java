package com.hongphat.common_service.advice;

import com.hongphat.common_service.enumerate.ErrorCode;
import com.hongphat.common_service.exception.BusinessException;
import com.hongphat.common_service.model.ErrorMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global Exception Handler for the application.
 * Handles different types of exceptions and returns appropriate error responses.
 *
 * @author hongp
 * @createDay 07/01/2025
 */
@ControllerAdvice
public class ExceptionHandle {

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ErrorMessage> handleBusinessException(BusinessException ex) {
		return buildErrorResponse(ex, ex.getErrorCode());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorMessage> handleValidationException(MethodArgumentNotValidException ex) {
		return buildErrorResponse(ex, ErrorCode.VALIDATION_ERROR);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorMessage> handleGenericException(Exception ex) {
		return buildErrorResponse(ex, ErrorCode.INTERNAL_ERROR);
	}

	private ResponseEntity<ErrorMessage> buildErrorResponse(Exception ex, ErrorCode errorCode) {
		ErrorMessage error = ErrorMessage.builder()
				.code(errorCode.getCode())
				.message(errorCode.getMessage())
				.status(errorCode.getStatus())
				.details(ex.getMessage())
				.timestamp(System.currentTimeMillis())
				.build();
		return new ResponseEntity<>(error, errorCode.getStatus());
	}
}