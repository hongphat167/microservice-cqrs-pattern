package com.hongphat.employeeservice.command.event;

import lombok.*;

/**
 * CreateEmployeeEvent
 *
 * @author hongp
 * @description Happy Coding With Phat 😊😊
 * @since 8 :44 CH 08/01/2025
 */
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateEmployeeEvent {
	private String id;
	private String firstName;
	private String lastName;
	private String kin;
	private Boolean isDisciplined;
}
