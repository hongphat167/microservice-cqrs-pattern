package com.hongphat.employeeservice.command.aggregate;

import com.hongphat.employeeservice.command.command_handling.CreateEmployeeCommand;
import com.hongphat.employeeservice.command.command_handling.DeleteEmployeeCommand;
import com.hongphat.employeeservice.command.command_handling.UpdateEmployeeCommand;
import com.hongphat.employeeservice.command.event.CreateEmployeeEvent;
import com.hongphat.employeeservice.command.event.DeleteEmployeeEvent;
import com.hongphat.employeeservice.command.event.UpdateEmployeeEvent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

/**
 * EmployeeAggregate
 *
 * @author hongp
 * @description Happy Coding With Phat 😊😊
 * @since 9 :15 CH 08/01/2025
 */
@Aggregate
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmployeeAggregate {
	@AggregateIdentifier
	private String id;
	private String firstName;
	private String lastName;
	private String kin;
	private Boolean isDisciplined;

	/**
	 * Instantiates a new Employee aggregate.
	 *
	 * @param command the command
	 */
	@CommandHandler
	public EmployeeAggregate(CreateEmployeeCommand command) {
		CreateEmployeeEvent event = CreateEmployeeEvent
				.builder()
				.id(command.getId())
				.firstName(command.getFirstName())
				.lastName(command.getLastName())
				.kin(command.getKin())
				.isDisciplined(command.getIsDisciplined())
				.build();

		AggregateLifecycle.apply(event);
	}

	/**
	 * On.
	 *
	 * @param event the event
	 */
	@EventSourcingHandler
	public void on(CreateEmployeeEvent event) {
		this.id = event.getId();
		this.firstName = event.getFirstName();
		this.lastName = event.getLastName();
		this.kin = event.getKin();
		this.isDisciplined = event.getIsDisciplined();
	}

	/**
	 * Handle.
	 *
	 * @param command the command
	 */
	@CommandHandler
	public void handle(UpdateEmployeeCommand command) {
		UpdateEmployeeEvent event = UpdateEmployeeEvent
				.builder()
				.id(command.getId())
				.firstName(command.getFirstName())
				.lastName(command.getLastName())
				.kin(command.getKin())
				.isDisciplined(command.getIsDisciplined())
				.build();

		AggregateLifecycle.apply(event);
	}

	/**
	 * On.
	 *
	 * @param event the event
	 */
	@EventSourcingHandler
	public void on(UpdateEmployeeEvent event) {
		this.id = event.getId();
		this.firstName = event.getFirstName();
		this.lastName = event.getLastName();
		this.kin = event.getKin();
		this.isDisciplined = event.getIsDisciplined();
	}

	/**
	 * Handle.
	 *
	 * @param command the command
	 */
	@CommandHandler
	public void handle(DeleteEmployeeCommand command) {
		DeleteEmployeeEvent event = DeleteEmployeeEvent
				.builder()
				.id(command.getId())
				.build();

		AggregateLifecycle.apply(event);
	}

	/**
	 * On.
	 *
	 * @param event the event
	 */
	@EventSourcingHandler
	public void on(DeleteEmployeeEvent event) {
		this.id = event.getId();
	}
}
