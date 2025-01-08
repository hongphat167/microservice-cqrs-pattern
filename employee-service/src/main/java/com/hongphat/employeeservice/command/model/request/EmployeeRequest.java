package com.hongphat.employeeservice.command.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * CreateEmployeeRequest
 *
 * @author hongp
 * @description Happy Coding With Phat 😊😊
 * @since 9 :05 CH 08/01/2025
 */
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class EmployeeRequest {
	private String id;
	@NotBlank(message = "firstName can not be blank")
	private String firstName;
	@NotBlank(message = "lastName can not be blank")
	private String lastName;
	@NotBlank(message = "kin can not be blank")
	private String kin;
	private Boolean isDisciplined;
}
