package com.hongphat.bookservice.query.controller;

import com.hongphat.bookservice.query.model.response.BookResponseModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

	/**
	 * Gets book by id.
	 *
	 * @param bookId the book id
	 * @return the book by id
	 */
	@GetMapping("{bookId}")
	BookResponseModel getBookById(@PathVariable String bookId);

	@PostMapping("/sendMessage")
	public void sendMessage(@RequestBody String message);

}
