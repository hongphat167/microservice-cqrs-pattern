package com.hongphat.borrowservice.command.handler;

import com.hongphat.borrowservice.command.event.CreateBorrowEvent;
import com.hongphat.borrowservice.command.event.DeleteBorrowEvent;
import com.hongphat.borrowservice.module.factory.IBorrowFactory;
import com.hongphat.common_service.enumerate.ErrorCode;
import com.hongphat.common_service.exception.BusinessException;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

/**
 * BorrowEventHandler
 *
 * @author hongp
 * @description Happy Coding With Phat 😊😊
 * @since 7 :15 CH 14/01/2025
 */
@Component
public class BorrowEventHandler {

	private final IBorrowFactory borrowFactory;

	/**
	 * Instantiates a new Borrow event handler.
	 *
	 * @param borrowFactory the borrow factory
	 */
	protected BorrowEventHandler(IBorrowFactory borrowFactory) {
		this.borrowFactory = borrowFactory;
	}

	/**
	 * Handle.
	 *
	 * @param event the event
	 */
	@EventHandler
	public void handle(CreateBorrowEvent event) {
		try {
			borrowFactory.create(event);
		} catch (BusinessException e) {
			throw new BusinessException(ErrorCode.BUSINESS_ERROR, e.getMessage());
		}
	}

	/**
	 * Handle.
	 *
	 * @param event the event
	 */
	@EventHandler
	public void handle(DeleteBorrowEvent event) {
		try {
			borrowFactory.delete(event.getId());
		} catch (BusinessException e) {
			throw new BusinessException(ErrorCode.BUSINESS_ERROR, e.getMessage());
		}
	}
}
