package com.hongphat.common_service.common;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * The type Api response.
 *
 * @param <T> the type parameter
 */
@Getter
@Builder
public class ApiResponse<T> {
	private String status;
	private String message;
	private T data;
	private LocalDateTime timestamp;
}
