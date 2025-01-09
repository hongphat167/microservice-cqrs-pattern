package com.hongphat.common_service.advice;

import com.hongphat.common_service.enumerate.ErrorCode;
import com.hongphat.common_service.exception.BusinessException;
import com.hongphat.common_service.model.ErrorMessage;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Global Exception Handler for the application.
 * Handles different types of exceptions and returns appropriate error responses.
 *
 * @author hongp
 * @createDay 07 /01/2025
 */
@ControllerAdvice
public class ExceptionHandle {

	/**
	 * Handle business exception response entity.
	 *
	 * @param ex the ex
	 * @return the response entity
	 */
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ErrorMessage> handleBusinessException(BusinessException ex) {
		return buildErrorResponse(ex, ex.getErrorCode());
	}

	/**
	 * Handle validation exception response entity.
	 *
	 * @param ex the ex
	 * @return the response entity
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
		Map<String, String> map = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			map.put(fieldName, errorMessage);
		});
		return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handle generic exception response entity.
	 *
	 * @param ex the ex
	 * @return the response entity
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorMessage> handleGenericException(Exception ex) {
		return buildErrorResponse(ex, ErrorCode.INTERNAL_ERROR);
	}

	private ResponseEntity<ErrorMessage> buildErrorResponse(Exception ex, ErrorCode errorCode) {
		ErrorMessage error = ErrorMessage.builder()
				.code(errorCode.getCode())
				.message(errorCode.getMessage())
				.details(ex.getMessage())
				.timestamp(LocalDateTime.now())
				.build();
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}