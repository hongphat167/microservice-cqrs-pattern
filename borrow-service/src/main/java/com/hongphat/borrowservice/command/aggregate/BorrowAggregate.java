package com.hongphat.borrowservice.command.aggregate;

import com.hongphat.borrowservice.command.command_handling.CreateBorrowCommand;
import com.hongphat.borrowservice.command.event.CreateBorrowEvent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.time.LocalDateTime;

/**
 * BorrowAggregate
 *
 * @author hongp
 * @description Happy Coding With Phat 😊😊
 * @since 7 :07 CH 14/01/2025
 */
@Aggregate
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BorrowAggregate {
	@AggregateIdentifier
	private String id;
	private String bookId;
	private String employeeId;
	private LocalDateTime borrowDate;
	private LocalDateTime returnDate;

	/**
	 * Instantiates a new Borrow aggregate.
	 *
	 * @param command the command
	 */
	@CommandHandler
	public BorrowAggregate(CreateBorrowCommand command) {
		CreateBorrowEvent event = CreateBorrowEvent
				.builder()
				.id(command.getId())
				.bookId(command.getBookId())
				.employeeId(command.getEmployeeId())
				.borrowDate(command.getBorrowDate())
				.build();

		AggregateLifecycle.apply(event);
	}

	/**
	 * On.
	 *
	 * @param event the event
	 */
	@EventSourcingHandler
	public void on(CreateBorrowEvent event) {
		this.id = event.getId();
		this.bookId = event.getBookId();
		this.employeeId = event.getEmployeeId();
		this.borrowDate = event.getBorrowDate();
	}
}
