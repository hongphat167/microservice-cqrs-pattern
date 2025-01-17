package com.hongphat.employeeservice.module.factory;

import com.hongphat.common_service.common.BaseCrudFactory;
import com.hongphat.employeeservice.command.event.CreateEmployeeEvent;
import com.hongphat.employeeservice.command.model.EmployeeModel;

import java.util.List;

/**
 * The interface Employee factory.
 */
public interface IEmployeeFactory extends BaseCrudFactory<EmployeeModel, CreateEmployeeEvent> {
	/**
	 * Find by is disciplined list.
	 *
	 * @param isDisciplined the is disciplined
	 * @return the list
	 */
	List<EmployeeModel> findByIsDisciplined(Boolean isDisciplined);
}
