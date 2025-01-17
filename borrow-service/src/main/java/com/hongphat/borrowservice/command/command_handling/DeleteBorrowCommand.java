package com.hongphat.borrowservice.command.command_handling;

import lombok.*;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

/**
 * DeleteBorrowCommand
 *
 * @author hongp
 * @description Happy Coding With Phat 😊😊
 * @since 10:39 CH 14/01/2025
 */
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class DeleteBorrowCommand {
	@TargetAggregateIdentifier
	private String id;
}
