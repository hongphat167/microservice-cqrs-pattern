package com.hongphat.borrowservice.model;

import lombok.*;

/**
 * EmployeeResponseModel
 *
 * @author hongp
 * @description Happy Coding With Phat 😊😊
 * @since 10 :09 CH 18/01/2025
 */
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class EmployeeResponseModel {
	private String id;
	private String firstName;
	private String lastName;
	private String kin;
	private Boolean isDisciplined;
}
