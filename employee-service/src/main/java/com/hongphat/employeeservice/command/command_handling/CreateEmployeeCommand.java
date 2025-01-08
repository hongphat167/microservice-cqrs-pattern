package com.hongphat.employeeservice.command.command_handling;

import lombok.*;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

/**
 * CreateEmployeeCommand
 *
 * @author hongp
 * @description Happy Coding With Phat 😊😊
 * @since 8 :42 CH 08/01/2025
 */
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateEmployeeCommand {
	@TargetAggregateIdentifier
	private String id;
	private String firstName;
	private String lastName;
	private String kin;
	private Boolean isDisciplined;
}
