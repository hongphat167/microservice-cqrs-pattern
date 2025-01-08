package com.hongphat.employeeservice.command.event;

import lombok.*;

/**
 * UpdateEmployeeEvent
 *
 * @author hongp
 * @description Happy Coding With Phat 😊😊
 * @since 10:05 CH 08/01/2025
 */
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateEmployeeEvent {
	private String id;
	private String firstName;
	private String lastName;
	private String kin;
	private Boolean isDisciplined;
}
