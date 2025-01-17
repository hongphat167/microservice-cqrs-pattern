package com.hongphat.bookservice.command.aggregate;

import com.hongphat.bookservice.command.command_handling.CreateBookCommand;
import com.hongphat.bookservice.command.command_handling.DeleteBookCommand;
import com.hongphat.bookservice.command.command_handling.UpdateBookCommand;
import com.hongphat.bookservice.command.command_handling.UpdateBookStatusCommand;
import com.hongphat.bookservice.command.event.BookCreateEvent;
import com.hongphat.bookservice.command.event.BookDeleteEvent;
import com.hongphat.bookservice.command.event.BookUpdatedEvent;
import com.hongphat.bookservice.command.event.BookUpdatedStatusEvent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

/**
 * The type Book aggregate.
 *
 * @author hongp
 * @createDay 05 /01/2025
 * @description Happy Coding With Phat 😊😊
 */
@Aggregate
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookAggregate {
	@AggregateIdentifier
	private String id;
	private String name;
	private String author;
	private Boolean isReady;

	/**
	 * Create new book aggregate.
	 *
	 * @param command the create book command
	 */
	@CommandHandler
	public BookAggregate(CreateBookCommand command) {
		BookCreateEvent event = BookCreateEvent
				.builder()
				.id(command.getId())
				.name(command.getName())
				.author(command.getAuthor())
				.isReady(command.getIsReady())
				.build();

		AggregateLifecycle.apply(event);
	}

	/**
	 * Handle.
	 *
	 * @param command the command
	 */
	@CommandHandler
	public void handle(UpdateBookCommand command) {
		BookUpdatedEvent event = BookUpdatedEvent
				.builder()
				.id(command.getId())
				.name(command.getName())
				.author(command.getAuthor())
				.isReady(command.getIsReady())
				.build();

		AggregateLifecycle.apply(event);
	}

	/**
	 * Handle.
	 *
	 * @param command the command
	 */
	@CommandHandler
	public void handle(UpdateBookStatusCommand command) {
		BookUpdatedStatusEvent event = BookUpdatedStatusEvent
				.builder()
				.id(command.getId())
				.isReady(command.getIsReady())
				.build();

		AggregateLifecycle.apply(event);
	}

	/**
	 * Handle.
	 *
	 * @param command the command
	 */
	@CommandHandler
	public void handle(DeleteBookCommand command) {
		BookDeleteEvent event = BookDeleteEvent
				.builder()
				.id(command.getId())
				.build();

		AggregateLifecycle.apply(event);
	}

	/**
	 * On book created event.
	 *
	 * @param event the book create event
	 */
	@EventSourcingHandler
	public void on(BookCreateEvent event) {
		this.id = event.getId();
		this.name = event.getName();
		this.author = event.getAuthor();
		this.isReady = event.getIsReady();
	}

	/**
	 * On.
	 *
	 * @param event the event
	 */
	@EventSourcingHandler
	public void on(BookUpdatedEvent event) {
		this.id = event.getId();
		this.name = event.getName();
		this.author = event.getAuthor();
		this.isReady = event.getIsReady();
	}

	/**
	 * On.
	 *
	 * @param event the event
	 */
	@EventSourcingHandler
	public void on(BookUpdatedStatusEvent event) {
		this.id = event.getId();
		this.isReady = event.getIsReady();
	}

	/**
	 * On.
	 *
	 * @param event the event
	 */
	@EventSourcingHandler
	public void on(BookDeleteEvent event) {
		this.id = event.getId();
	}
}