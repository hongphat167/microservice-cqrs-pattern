package com.hongphat.bookservice.module.factory;

import com.hongphat.bookservice.command.event.BookCreateEvent;
import com.hongphat.bookservice.command.event.BookUpdatedStatusEvent;
import com.hongphat.bookservice.command.model.BookModel;
import com.hongphat.common_service.common.BaseCrudFactory;


/**
 * The interface Book factory.
 *
 * @author hongp
 * @createDay 05 /01/2025
 * @description Happy Coding With Phat 😊😊
 */
public interface IBookFactory extends BaseCrudFactory<BookModel, BookCreateEvent>  {
	void updateIsRead(BookUpdatedStatusEvent event);
}