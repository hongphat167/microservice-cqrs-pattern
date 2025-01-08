package com.hongphat.employeeservice.command.controller.impl;

import com.hongphat.employeeservice.command.command_handling.CreateEmployeeCommand;
import com.hongphat.employeeservice.command.command_handling.DeleteEmployeeCommand;
import com.hongphat.employeeservice.command.command_handling.UpdateEmployeeCommand;
import com.hongphat.employeeservice.command.controller.IEmployeeController;
import com.hongphat.employeeservice.command.model.request.EmployeeRequest;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * EmployeeController
 *
 * @author hongp
 * @description Happy Coding With Phat 😊😊
 * @since 8 :46 CH 08/01/2025
 */
@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController implements IEmployeeController {

	private final CommandGateway commandGateway;

	/**
	 * Instantiates a new Employee controller.
	 *
	 * @param commandGateway the command gateway
	 */
	protected EmployeeController(CommandGateway commandGateway) {
		this.commandGateway = commandGateway;
	}

	@Override
	public String addEmployee(EmployeeRequest request) {
		CreateEmployeeCommand command = CreateEmployeeCommand
				.builder()
				.id(UUID.randomUUID().toString())
				.firstName(request.getFirstName())
				.lastName(request.getLastName())
				.kin(request.getKin())
				.isDisciplined(true)
				.build();

		return commandGateway.sendAndWait(command);
	}

	@Override
	public String updateEmployee(EmployeeRequest request, String employeeId) {
		UpdateEmployeeCommand command = UpdateEmployeeCommand
				.builder()
				.id(employeeId)
				.firstName(request.getFirstName())
				.lastName(request.getLastName())
				.kin(request.getKin())
				.isDisciplined(request.getIsDisciplined())
				.build();

		return commandGateway.sendAndWait(command);
	}

	@Override
	public String deleteEmployee(String employeeId) {
		DeleteEmployeeCommand command = DeleteEmployeeCommand
				.builder()
				.id(employeeId)
				.build();

		return commandGateway.sendAndWait(command);
	}
}
