package com.hongphat.bookservice.query.controller;

import com.hongphat.bookservice.query.model.response.BookResponseModel;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * The interface Book query controller.
 *
 * @author hongp
 * @createDay 05 /01/2025
 * @description Happy Coding With Phat 😊😊
 */

public interface IBookQueryController {

	/**
	 * Gets all books.
	 *
	 * @return the all books
	 */
	@GetMapping
	List<BookResponseModel> getAllBooks();
}
