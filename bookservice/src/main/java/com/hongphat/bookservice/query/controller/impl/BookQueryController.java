package com.hongphat.bookservice.query.controller.impl;

import com.hongphat.bookservice.query.controller.IBookQueryController;
import com.hongphat.bookservice.query.model.response.BookResponseModel;
import com.hongphat.bookservice.query.queries.GetAllBookQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The type Book query controller.
 *
 * @author hongp
 * @createDay 05 /01/2025
 * @description Happy Coding With Phat 😊😊
 */
@RestController
@RequestMapping("api/v1/books")
public class BookQueryController implements IBookQueryController {

	private final QueryGateway queryGateway;

	/**
	 * Instantiates a new Book query controller.
	 *
	 * @param queryGateway the query gateway
	 */
	public BookQueryController(QueryGateway queryGateway) {
		this.queryGateway = queryGateway;
	}

	@Override
	public List<BookResponseModel> getAllBooks() {

		GetAllBookQuery query = GetAllBookQuery.builder().build();

		return queryGateway.query(
				query, ResponseTypes.multipleInstancesOf(BookResponseModel.class)
		).join();
	}
}
