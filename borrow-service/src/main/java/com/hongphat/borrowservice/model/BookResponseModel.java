package com.hongphat.borrowservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * BookResponseModel
 *
 * @author hongp
 * @description Happy Coding With Phat 😊😊
 * @since 10:07 CH 14/01/2025
 */
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class BookResponseModel {
	private String id;
	private String name;
	private String author;
	private Boolean isReady;
}
