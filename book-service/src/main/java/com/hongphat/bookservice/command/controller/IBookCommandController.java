package com.hongphat.bookservice.command.controller;

import com.hongphat.bookservice.command.model.request.BookModelRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * The interface Book command controller.
 *
 * @author hongp
 * @createDay 05 /01/2025
 * @description Happy Coding With Phat 😊😊
 */
public interface IBookCommandController {
	/**
	 * Add book string.
	 *
	 * @param model the model
	 * @return the string
	 */
	@PostMapping
	String addBook(@Valid @RequestBody BookModelRequest model);

	/**
	 * Update book string.
	 *
	 * @param model  the model
	 * @param bookId the book id
	 * @return the string
	 */
	@PutMapping("/{bookId}")
	String updateBook(@Valid @RequestBody BookModelRequest model, @PathVariable String bookId);

	/**
	 * Delete book string.
	 *
	 * @param bookId the book id
	 * @return the string
	 */
	@DeleteMapping("/{bookId}")
	String deleteBook(@PathVariable String bookId);
}
