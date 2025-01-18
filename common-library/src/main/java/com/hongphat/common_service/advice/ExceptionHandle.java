package com.hongphat.common_service.advice;

import com.hongphat.common_service.enumerate.ErrorCode;
import com.hongphat.common_service.exception.BusinessException;
import com.hongphat.common_service.model.ErrorMessage;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Global Exception Handler for the application.
 * Handles different types of exceptions and returns appropriate error responses.
 *
 * @author hongp
 * @createDay 07 /01/2025
 */
@ControllerAdvice
@Slf4j
public class ExceptionHandle {

	/**
	 * Handle business exception
	 *
	 * @param ex the ex
	 * @return the response entity
	 */
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ErrorMessage> handleBusinessException(BusinessException ex) {
		log.error("Business Exception - Code: {}, Message: {}, Details: {}",
				ex.getErrorCode(), ex.getMessage(), ex.getDetails());
		return buildErrorResponse(ex, ex.getErrorCode());
	}

	/**
	 * Handle entity not found exception
	 *
	 * @param ex the ex
	 * @return the response entity
	 */
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ErrorMessage> handleEntityNotFoundException(EntityNotFoundException ex) {
		log.error("Entity Not Found Exception: {}", ex.getMessage(), ex);
		return buildErrorResponse(ex, ErrorCode.ENTITY_NOT_FOUND);
	}

	/**
	 * Handle validation exceptions
	 *
	 * @param ex the ex
	 * @return the response entity
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
		log.error("Validation Exception: {}", ex.getMessage(), ex);
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach(error -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handle data integrity violation exception
	 *
	 * @param ex the ex
	 * @return the response entity
	 */
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ErrorMessage> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
		log.error("Data Integrity Violation Exception: {}", ex.getMessage(), ex);
		return buildErrorResponse(ex, ErrorCode.DATA_INTEGRITY_VIOLATION);
	}

	/**
	 * Handle method argument type mismatch exception
	 *
	 * @param ex the ex
	 * @return the response entity
	 */
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ErrorMessage> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
		log.error("Method Argument Type Mismatch Exception: {}", ex.getMessage(), ex);
		String error = String.format("The parameter '%s' of value '%s' could not be converted to type '%s'",
				ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());
		return buildErrorResponse(new Exception(error), ErrorCode.INVALID_PARAMETER);
	}

	/**
	 * Handle all other exceptions
	 *
	 * @param ex the ex
	 * @return the response entity
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorMessage> handleGenericException(Exception ex) {
		log.error("Unhandled Exception: {}", ex.getMessage(), ex);
		return buildErrorResponse(ex, ErrorCode.INTERNAL_ERROR);
	}

	/**
	 * Build error response with appropriate status
	 */
	private ResponseEntity<ErrorMessage> buildErrorResponse(Exception ex, ErrorCode errorCode) {
		ErrorMessage error = ErrorMessage.builder()
				.code(errorCode.getCode())
				.message(errorCode.getMessage())
				.details(ex.getMessage())
				.timestamp(LocalDateTime.now())
				.requestId(UUID.randomUUID().toString())
				.build();

		HttpStatus status = mapErrorCodeToHttpStatus(errorCode);
		return new ResponseEntity<>(error, status);
	}


	/**
	 * Map error code to HTTP status
	 */
	private HttpStatus mapErrorCodeToHttpStatus(ErrorCode errorCode) {
		return switch (errorCode) {
			case VALIDATION_ERROR, BUSINESS_ERROR, INVALID_PARAMETER -> HttpStatus.BAD_REQUEST;
			case ACCESS_DENIED -> HttpStatus.FORBIDDEN;
			case ENTITY_NOT_FOUND -> HttpStatus.NOT_FOUND;
			case INVALID_CREDENTIALS -> HttpStatus.UNAUTHORIZED;
			case DATA_INTEGRITY_VIOLATION -> HttpStatus.CONFLICT;
			default -> HttpStatus.INTERNAL_SERVER_ERROR;
		};
	}
}