package com.hongphat.borrowservice.command.controller.impl;

import com.hongphat.borrowservice.command.command_handling.CreateBorrowCommand;
import com.hongphat.borrowservice.command.controller.IBorrowCommandController;
import com.hongphat.borrowservice.command.model.request.BorrowRequest;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * BorrowCommandController
 *
 * @author hongp
 * @description Happy Coding With Phat 😊😊
 * @since 1 :53 CH 12/01/2025
 */
@RestController
@RequestMapping("api/v1/borrows")
public class BorrowCommandController implements IBorrowCommandController {

	private final CommandGateway commandGateway;

	/**
	 * Instantiates a new Borrow command controller.
	 *
	 * @param commandGateway the command gateway
	 */
	protected BorrowCommandController(CommandGateway commandGateway) {
		this.commandGateway = commandGateway;
	}

	@Override
	public String createBorrow(BorrowRequest request) {
		CreateBorrowCommand command = CreateBorrowCommand.builder()
				.id(UUID.randomUUID().toString())
				.bookId(request.getBookId())
				.employeeId(request.getEmployeeId())
				.borrowDate(LocalDateTime.now())
				.build();

		return commandGateway.sendAndWait(command);
	}
}
