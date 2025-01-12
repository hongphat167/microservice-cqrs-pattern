package com.hongphat.borrowservice.command.command_handling;

import lombok.*;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.LocalDateTime;

/**
 * CreateBorrowCommand
 *
 * @author hongp
 * @description Happy Coding With Phat 😊😊
 * @since 6:38 CH 12/01/2025
 */
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateBorrowCommand {
	@TargetAggregateIdentifier
	private String id;
	private String bookId;
	private String employeeId;
	private LocalDateTime borrowDate;
}
