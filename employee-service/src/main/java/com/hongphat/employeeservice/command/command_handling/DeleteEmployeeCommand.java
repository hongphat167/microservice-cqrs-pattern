package com.hongphat.employeeservice.command.command_handling;

import lombok.*;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

/**
 * DeleteEmployeeCommand
 *
 * @author hongp
 * @description Happy Coding With Phat 😊😊
 * @since 10 :13 CH 08/01/2025
 */
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class DeleteEmployeeCommand {
	@TargetAggregateIdentifier
	private String id;
}
