package com.hongphat.bookservice.command.command_handling;

import lombok.*;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

/**
 * The type Delete book command.
 *
 * @author hongp
 * @createDay 05 /01/2025
 * @description Happy Coding With Phat 😊😊
 */
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class DeleteBookCommand {
	@TargetAggregateIdentifier
	private String id;
}
