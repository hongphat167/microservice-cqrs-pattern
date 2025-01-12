package com.hongphat.borrowservice.command.controller;

import com.hongphat.borrowservice.command.model.request.BorrowRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * IBorrowCommandController
 *
 * @author hongp
 * @description Happy Coding With Phat 😊😊
 * @since 1:52 CH 12/01/2025
 */
public interface IBorrowCommandController {

	@PostMapping
	String createBorrow(@RequestBody BorrowRequest request);
}
