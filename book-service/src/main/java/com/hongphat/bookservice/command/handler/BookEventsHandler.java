package com.hongphat.bookservice.command.handler;

import com.hongphat.bookservice.command.event.BookCreateEvent;
import com.hongphat.bookservice.command.event.BookDeleteEvent;
import com.hongphat.bookservice.command.event.BookUpdatedEvent;
import com.hongphat.bookservice.command.model.BookModel;
import com.hongphat.bookservice.module.factory.IBookFactory;
import com.hongphat.common_service.enumerate.ErrorCode;
import com.hongphat.common_service.exception.BusinessException;
import org.axonframework.eventhandling.DisallowReplay;
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
	@DisallowReplay
	public void onExecute(BookCreateEvent eventListener) {
		try {
			iBookFactory.create(eventListener);
		} catch (BusinessException e) {
			throw new BusinessException(ErrorCode.BUSINESS_ERROR, e.getMessage());
		}
	}

	/**
	 * On execute.
	 *
	 * @param eventListener the event listener
	 */
	@EventHandler
	@DisallowReplay
	public void onExecute(BookUpdatedEvent eventListener) {
		try {
			BookModel model = BookModel
					.builder()
					.id(eventListener.getId())
					.name(eventListener.getName())
					.author(eventListener.getAuthor())
					.isReady(eventListener.getIsReady())
					.build();
			iBookFactory.update(eventListener.getId(), model);
		} catch (BusinessException e) {
			throw new BusinessException(ErrorCode.BUSINESS_ERROR, e.getMessage());
		}

	}

	/**
	 * On execute.
	 *
	 * @param eventListener the event listener
	 */
	@EventHandler
	@DisallowReplay
	public void onExecute(BookDeleteEvent eventListener) {
		try {
			iBookFactory.delete(eventListener.getId());
		} catch (BusinessException e) {
			throw new BusinessException(ErrorCode.BUSINESS_ERROR, e.getMessage());
		}

	}
}
