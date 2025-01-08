package com.hongphat.employeeservice.command.model;

import lombok.*;

/**
 * EmployeeModel
 *
 * @author hongp
 * @since 8:39 CH 08/01/2025
 * @description Happy Coding With Phat 😊😊
 */
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class EmployeeModel {
	private String id;
	private String firstName;
	private String lastName;
	private String kin;
	private Boolean isDisciplined;
}
