package com.hongphat.bookservice.command.handler;

import com.hongphat.bookservice.command.event.BookCreateEvent;
import com.hongphat.bookservice.command.event.BookDeleteEvent;
import com.hongphat.bookservice.command.event.BookUpdatedEvent;
import com.hongphat.bookservice.command.model.BookModel;
import com.hongphat.bookservice.command.module.factory.IBookFactory;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

/**
 * The type Book events handler.
 *
 * @author hongp
 * @createDay 05 /01/2025
 * @description Happy Coding With Phat 😊😊
 */
@Component
public class BookEventsHandler {

	private final IBookFactory iBookFactory;

	/**
	 * Instantiates a new Book events handler.
	 *
	 * @param iBookFactory the book factory
	 */
	protected BookEventsHandler(IBookFactory iBookFactory) {
		this.iBookFactory = iBookFactory;
	}

	/**
	 * On execute.
	 *
	 * @param eventListener the event listener
	 */
	@EventHandler
	public void onExecute(BookCreateEvent eventListener) {
		iBookFactory.createAndSave(eventListener);
	}

	/**
	 * On execute.
	 *
	 * @param eventListener the event listener
	 */
	@EventHandler
	public void onExecute(BookUpdatedEvent eventListener) {
		BookModel model = BookModel
				.builder()
				.id(eventListener.getId())
				.name(eventListener.getName())
				.author(eventListener.getAuthor())
				.isReady(eventListener.getIsReady())
				.build();
		iBookFactory.updateBook(eventListener.getId(), model);
	}

	/**
	 * On execute.
	 *
	 * @param eventListener the event listener
	 */
	@EventHandler
	public void onExecute(BookDeleteEvent eventListener) {
		iBookFactory.deleteById(eventListener.getId());
	}
}
