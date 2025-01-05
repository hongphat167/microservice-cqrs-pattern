package com.hongphat.bookservice.command.controller.impl;

import com.hongphat.bookservice.command.command_handling.CreateBookCommand;
import com.hongphat.bookservice.command.command_handling.DeleteBookCommand;
import com.hongphat.bookservice.command.command_handling.UpdateBookCommand;
import com.hongphat.bookservice.command.controller.IBookCommandController;
import com.hongphat.bookservice.command.model.request.BookModelRequest;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * The type Book command controller.
 *
 * @author hongp
 * @createDay 05 /01/2025
 * @description Happy Coding With Phat 😊😊
 */
@RestController
@RequestMapping("/api/v1/books")
public class BookCommandController implements IBookCommandController {

	private final CommandGateway commandGateway;

	/**
	 * Instantiates a new Book command controller.
	 *
	 * @param commandGateway the command gateway
	 */
	protected BookCommandController(CommandGateway commandGateway) {
		this.commandGateway = commandGateway;
	}

	@Override
	public String addBook(BookModelRequest model) {
		CreateBookCommand command = CreateBookCommand
				.builder()
				.id(UUID.randomUUID().toString())
				.name(model.getName())
				.author(model.getAuthor())
				.isReady(true)
				.build();

		return commandGateway.sendAndWait(command);
	}

	@Override
	public String updateBook(BookModelRequest model, String bookId) {
		UpdateBookCommand command = UpdateBookCommand
				.builder()
				.id(bookId)
				.name(model.getName())
				.author(model.getAuthor())
				.isReady(model.getIsReady())
				.build();

		return commandGateway.sendAndWait(command);
	}

	@Override
	public String deleteBook(String bookId) {
		DeleteBookCommand command = DeleteBookCommand
				.builder()
				.id(bookId)
				.build();

		return commandGateway.sendAndWait(command);

	}
}
