package com.hongphat.employeeservice.command.handler;

import com.hongphat.employeeservice.command.event.CreateEmployeeEvent;
import com.hongphat.employeeservice.command.event.DeleteEmployeeEvent;
import com.hongphat.employeeservice.command.event.UpdateEmployeeEvent;
import com.hongphat.employeeservice.command.model.EmployeeModel;
import com.hongphat.employeeservice.command.module.factory.IEmployeeFactory;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

/**
 * EmployeeEventsHandler
 *
 * @author hongp
 * @description Happy Coding With Phat 😊😊
 * @since 9 :22 CH 08/01/2025
 */
@Component
public class EmployeeEventsHandler {

	private final IEmployeeFactory employeeFactory;

	/**
	 * Instantiates a new Employee events handler.
	 *
	 * @param employeeFactory the employee factory
	 */
	protected EmployeeEventsHandler(IEmployeeFactory employeeFactory) {
		this.employeeFactory = employeeFactory;
	}

	/**
	 * On execute.
	 *
	 * @param event the event
	 */
	@EventHandler
	public void onExecute(CreateEmployeeEvent event) {
		employeeFactory.createAndSave(event);
	}

	/**
	 * On execute.
	 *
	 * @param event the event
	 */
	@EventHandler
	public void onExecute(UpdateEmployeeEvent event) {
		EmployeeModel model = EmployeeModel
				.builder()
				.id(event.getId())
				.firstName(event.getFirstName())
				.lastName(event.getLastName())
				.kin(event.getKin())
				.isDisciplined(event.getIsDisciplined())
				.build();

		employeeFactory.updateEmployee(event.getId(), model);
	}

	/**
	 * On execute.
	 *
	 * @param event the event
	 */
	@EventHandler
	public void onExecute(DeleteEmployeeEvent event) {
		employeeFactory.deleteById(event.getId());
	}
}
