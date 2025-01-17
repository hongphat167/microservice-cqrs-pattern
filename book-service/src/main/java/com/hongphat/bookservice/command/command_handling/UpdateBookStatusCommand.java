package com.hongphat.bookservice.command.command_handling;

import lombok.*;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

/**
 * UpdateBookStatusCommand
 *
 * @author hongp
 * @description Happy Coding With Phat 😊😊
 * @since 10 :27 CH 14/01/2025
 */
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateBookStatusCommand {
	@TargetAggregateIdentifier
	private String id;
	private Boolean isReady;
}
