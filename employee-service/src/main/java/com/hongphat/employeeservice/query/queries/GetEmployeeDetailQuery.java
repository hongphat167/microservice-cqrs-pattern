package com.hongphat.employeeservice.query.queries;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * GetEmployeeDetailQuery
 *
 * @author hongp
 * @description Happy Coding With Phat 😊😊
 * @since 7:51 CH 09/01/2025
 */
@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
public class GetEmployeeDetailQuery {
	private String id;
}
