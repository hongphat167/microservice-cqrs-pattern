package com.hongphat.employeeservice.query.controller.impl;

import com.hongphat.employeeservice.query.controller.IEmployeeQueryController;
import com.hongphat.employeeservice.query.model.response.EmployeeModelResponse;
import com.hongphat.employeeservice.query.queries.GetEmployeeByIsDisciplinedQuery;
import com.hongphat.employeeservice.query.queries.GetEmployeeDetailQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * EmployeeQueryController
 *
 * @author hongp
 * @description Happy Coding With Phat 😊😊
 * @since 7 :45 CH 09/01/2025
 */
@RestController
@RequestMapping("api/v1/employees")
public class EmployeeQueryController implements IEmployeeQueryController {

	private final QueryGateway queryGateway;

	/**
	 * Instantiates a new Employee query controller.
	 *
	 * @param queryGateway the query gateway
	 */
	protected EmployeeQueryController(QueryGateway queryGateway) {
		this.queryGateway = queryGateway;
	}

	@Override
	public EmployeeModelResponse getEmployee(String employeeId) {
		GetEmployeeDetailQuery query = GetEmployeeDetailQuery
				.builder()
				.id(employeeId)
				.build();

		return queryGateway.query(
				query, ResponseTypes.instanceOf(EmployeeModelResponse.class)
		).join();
	}

	@Override
	public List<EmployeeModelResponse> getEmployeeList(Boolean isDisciplined) {
		GetEmployeeByIsDisciplinedQuery query = GetEmployeeByIsDisciplinedQuery
				.builder()
				.isDisciplined(isDisciplined)
				.build();

		return queryGateway.query(
				query, ResponseTypes.multipleInstancesOf(EmployeeModelResponse.class)
		).join();
	}
}
