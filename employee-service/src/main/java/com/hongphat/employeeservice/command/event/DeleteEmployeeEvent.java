package com.hongphat.employeeservice.command.event;

import lombok.*;

/**
 * DeleteEmployeeEvent
 *
 * @author hongp
 * @description Happy Coding With Phat 😊😊
 * @since 10:06 CH 08/01/2025
 */
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class DeleteEmployeeEvent {
	private String id;
}
