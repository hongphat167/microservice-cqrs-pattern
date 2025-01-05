package com.hongphat.bookservice.command.module.factory;

import com.hongphat.bookservice.command.event.BookCreateEvent;
import com.hongphat.bookservice.command.model.BookModel;

import java.util.List;

/**
 * The interface Book factory.
 *
 * @author hongp
 * @createDay 05 /01/2025
 * @description Happy Coding With Phat 😊😊
 */
public interface IBookFactory {
	/**
	 * Create and save book from event.
	 *
	 * @param eventListener the book create event listener
	 */
	void createAndSave(BookCreateEvent eventListener);

	/**
	 * Find book by id.
	 *
	 * @param id the book id
	 * @return optional of book model
	 */
	BookModel findById(String id);

	/**
	 * Find all books.
	 *
	 * @return list of book models
	 */
	List<BookModel> findAll();

	/**
	 * Update book.
	 *
	 * @param id    the book id
	 * @param model the book model
	 */
	void updateBook(String id, BookModel model);

	/**
	 * Delete book by id.
	 *
	 * @param id the book id
	 */
	void deleteById(String id);
}