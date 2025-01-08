package com.hongphat.employeeservice.command.controller;

import com.hongphat.employeeservice.command.model.request.EmployeeRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * EmployeeController
 *
 * @author hongp
 * @description Happy Coding With Phat 😊😊
 * @since 8 :45 CH 08/01/2025
 */
public interface IEmployeeController {

	/**
	 * Add employee string.
	 *
	 * @param request the request
	 * @return the string
	 */
	@PostMapping
	String addEmployee(@Valid @RequestBody EmployeeRequest request);

	/**
	 * Update employee string.
	 *
	 * @param request    the request
	 * @param employeeId the employee id
	 * @return the string
	 */
	@PutMapping("{employeeId}")
	String updateEmployee(@Valid @RequestBody EmployeeRequest request, @PathVariable String employeeId);

	/**
	 * Delete employee string.
	 *
	 * @param employeeId the employee id
	 * @return the string
	 */
	@DeleteMapping("{employeeId}")
	String deleteEmployee(@PathVariable String employeeId);
}
