package com.hongphat.employeeservice.command.module.factory;

import com.hongphat.employeeservice.command.event.CreateEmployeeEvent;
import com.hongphat.employeeservice.command.model.EmployeeModel;

import java.util.List;

/**
 * The interface Employee factory.
 */
public interface IEmployeeFactory {
	/**
	 * Create and save.
	 *
	 * @param eventListener the event listener
	 */
	void createAndSave(CreateEmployeeEvent eventListener);

	/**
	 * Find by id employee model.
	 *
	 * @param id the id
	 * @return the employee model
	 */
	EmployeeModel findById(String id);

	/**
	 * Find all list.
	 *
	 * @return the list
	 */
	List<EmployeeModel> findAll();

	/**
	 * Update employee.
	 *
	 * @param id    the id
	 * @param model the model
	 */
	void updateEmployee(String id, EmployeeModel model);

	/**
	 * Delete by id.
	 *
	 * @param id the id
	 */
	void deleteById(String id);

	/**
	 * Find by is disciplined list.
	 *
	 * @param isDisciplined the is disciplined
	 * @return the list
	 */
	List<EmployeeModel> findByIsDisciplined(Boolean isDisciplined);
}
