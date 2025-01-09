package com.hongphat.employeeservice.query.model.response;

import lombok.*;

/**
 * EmployeeModelResponse
 *
 * @author hongp
 * @description Happy Coding With Phat 😊😊
 * @since 7 :36 CH 09/01/2025
 */
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class EmployeeModelResponse {
	private String id;
	private String firstName;
	private String lastName;
	private String kin;
	private Boolean isDisciplined;
}
