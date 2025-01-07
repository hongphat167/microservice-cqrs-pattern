package com.hongphat.bookservice.query.handler;

import com.hongphat.bookservice.command.model.BookModel;
import com.hongphat.bookservice.command.module.factory.IBookFactory;
import com.hongphat.bookservice.query.model.response.BookResponseModel;
import com.hongphat.bookservice.query.queries.GetAllBookQuery;
import com.hongphat.bookservice.query.queries.GetDetailBookQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Book query handler.
 *
 * @author hongp
 * @createDay 05 /01/2025
 * @description Happy Coding With Phat 😊😊
 */
@Component
public class BookQueryHandler {
	private final IBookFactory iBookFactory;

	/**
	 * Instantiates a new Book query handler.
	 *
	 * @param iBookFactory the book factory
	 */
	public BookQueryHandler(IBookFactory iBookFactory) {
		this.iBookFactory = iBookFactory;
	}

	/**
	 * Handle list.
	 *
	 * @param getAllBookQuery the get all book query
	 * @return the list
	 */
	@QueryHandler
	public List<BookResponseModel> handle(GetAllBookQuery getAllBookQuery) {
		List<BookModel> bookModels = iBookFactory.findAll();
		return bookModels.stream()
				.map(bookModel -> BookResponseModel.builder()
						.id(bookModel.getId())
						.author(bookModel.getAuthor())
						.name(bookModel.getName())
						.isReady(bookModel.getIsReady())
						.build())
				.collect(Collectors.toList());
	}

	/**
	 * Handle book response model.
	 *
	 * @param getDetailBookQuery the get detail book query
	 * @return the book response model
	 */
	@QueryHandler
	public BookResponseModel handle(GetDetailBookQuery getDetailBookQuery) {
		BookModel bookModel = iBookFactory.findById(getDetailBookQuery.getBookId());
		return BookResponseModel
				.builder()
				.id(bookModel.getId())
				.author(bookModel.getAuthor())
				.name(bookModel.getName())
				.isReady(bookModel.getIsReady())
				.build();
	}
}
