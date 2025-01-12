package com.hongphat.employeeservice.query.handler;

import com.hongphat.common_service.enumerate.ErrorCode;
import com.hongphat.common_service.exception.BusinessException;
import com.hongphat.employeeservice.command.model.EmployeeModel;
import com.hongphat.employeeservice.module.factory.IEmployeeFactory;
import com.hongphat.employeeservice.query.model.response.EmployeeModelResponse;
import com.hongphat.employeeservice.query.queries.GetEmployeeByIsDisciplinedQuery;
import com.hongphat.employeeservice.query.queries.GetEmployeeDetailQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * EmployeeQueryHandler
 *
 * @author hongp
 * @description Happy Coding With Phat 😊😊
 * @since 7 :53 CH 09/01/2025
 */
@Component
public class EmployeeQueryHandler {

	private final IEmployeeFactory employeeFactory;

	/**
	 * Instantiates a new Employee query handler.
	 *
	 * @param employeeFactory the employee factory
	 */
	protected EmployeeQueryHandler(IEmployeeFactory employeeFactory) {
		this.employeeFactory = employeeFactory;
	}

	/**
	 * Handle list.
	 *
	 * @param getEmployeeByIsDisciplinedQuery the get employee by is disciplined query
	 * @return the list
	 */
	@QueryHandler
	public List<EmployeeModelResponse> handle(GetEmployeeByIsDisciplinedQuery getEmployeeByIsDisciplinedQuery) {
		try {
			List<EmployeeModel> employeeModels = employeeFactory.findByIsDisciplined(
					getEmployeeByIsDisciplinedQuery.getIsDisciplined()
			);

			return employeeModels.stream()
					.map(employeeModel -> EmployeeModelResponse
							.builder()
							.id(employeeModel.getId())
							.firstName(employeeModel.getFirstName())
							.lastName(employeeModel.getLastName())
							.kin(employeeModel.getKin())
							.isDisciplined(employeeModel.getIsDisciplined())
							.build()
					).collect(Collectors.toList());
		} catch (BusinessException e) {
			throw new BusinessException(ErrorCode.BUSINESS_ERROR, e.getMessage());
		}
	}

	/**
	 * Handle employee model response.
	 *
	 * @param getEmployeeDetailQuery the get employee detail query
	 * @return the employee model response
	 */
	@QueryHandler
	public EmployeeModelResponse handle(GetEmployeeDetailQuery getEmployeeDetailQuery) {
		try {
			EmployeeModel employeeModel = employeeFactory.get(
					getEmployeeDetailQuery.getId()
			);

			return EmployeeModelResponse
					.builder()
					.id(employeeModel.getId())
					.firstName(employeeModel.getFirstName())
					.lastName(employeeModel.getLastName())
					.kin(employeeModel.getKin())
					.isDisciplined(employeeModel.getIsDisciplined())
					.build();
		} catch (BusinessException e) {
			throw new BusinessException(ErrorCode.BUSINESS_ERROR, e.getMessage());
		}

	}
}
