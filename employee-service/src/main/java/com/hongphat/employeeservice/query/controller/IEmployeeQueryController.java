package com.hongphat.employeeservice.query.controller;

import com.hongphat.employeeservice.query.model.response.EmployeeModelResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * IEmployeeQueryController
 *
 * @author hongp
 * @description Happy Coding With Phat 😊😊
 * @since 7 :31 CH 09/01/2025
 */
public interface IEmployeeQueryController {
	/**
	 * Gets employee list.
	 *
	 * @param isDisciplined the is disciplined
	 * @return the employee list
	 */
	@GetMapping
	List<EmployeeModelResponse> getEmployeeList(@RequestParam(required = false) Boolean isDisciplined);

	/**
	 * Gets employee.
	 *
	 * @param employeeId the employee id
	 * @return the employee
	 */
	@GetMapping("{employeeId}")
	EmployeeModelResponse getEmployee(@PathVariable String employeeId);
}
