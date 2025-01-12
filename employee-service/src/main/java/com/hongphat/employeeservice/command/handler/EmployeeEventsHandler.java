package com.hongphat.employeeservice.command.handler;

import com.hongphat.common_service.enumerate.ErrorCode;
import com.hongphat.common_service.exception.BusinessException;
import com.hongphat.employeeservice.command.event.CreateEmployeeEvent;
import com.hongphat.employeeservice.command.event.DeleteEmployeeEvent;
import com.hongphat.employeeservice.command.event.UpdateEmployeeEvent;
import com.hongphat.employeeservice.command.model.EmployeeModel;
import com.hongphat.employeeservice.module.factory.IEmployeeFactory;
import org.axonframework.eventhandling.DisallowReplay;
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
	@DisallowReplay
	public void onExecute(CreateEmployeeEvent event) {
		try {
			employeeFactory.create(event);
		} catch (BusinessException e) {
			throw new BusinessException(e.getErrorCode(), e.getMessage());
		}
	}

	/**
	 * On execute.
	 *
	 * @param event the event
	 */
	@EventHandler
	@DisallowReplay
	public void onExecute(UpdateEmployeeEvent event) {
		try {
			EmployeeModel model = EmployeeModel
					.builder()
					.id(event.getId())
					.firstName(event.getFirstName())
					.lastName(event.getLastName())
					.kin(event.getKin())
					.isDisciplined(event.getIsDisciplined())
					.build();

			employeeFactory.update(event.getId(), model);
		} catch (BusinessException e) {
			throw new BusinessException(ErrorCode.BUSINESS_ERROR, e.getMessage());
		}

	}

	/**
	 * On execute.
	 *
	 * @param event the event
	 */
	@EventHandler
	@DisallowReplay
	public void onExecute(DeleteEmployeeEvent event) {
		try {
			employeeFactory.delete(event.getId());
		} catch (BusinessException e) {
			throw new BusinessException(ErrorCode.BUSINESS_ERROR, e.getMessage());
		}
	}
}
