package com.hongphat.bookservice.command.command_handling;

import lombok.*;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

/**
 * @author hongp
 * @createDay 05/01/2025
 * @description Happy Coding With Phat 😊😊
 */
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateBookCommand {
	@TargetAggregateIdentifier
	private String id;
	private String name;
	private String author;
	private Boolean isReady;
}
