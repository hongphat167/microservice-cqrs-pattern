package com.hongphat.borrowservice.command.saga;

import com.hongphat.borrowservice.command.command_handling.DeleteBorrowCommand;
import com.hongphat.borrowservice.command.event.CreateBorrowEvent;
import com.hongphat.borrowservice.command.event.UpdateBookStatusEvent;
import com.hongphat.borrowservice.grpc_client.BookGrpcClient;
import com.hongphat.borrowservice.model.BookResponseModel;
import com.hongphat.common_service.enumerate.ErrorCode;
import com.hongphat.common_service.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * BorrowingSaga
 *
 * @author hongp
 * @description Happy Coding With Phat 😊😊
 * @since 9 :12 CH 14/01/2025
 */
@Saga
@Slf4j
public class BorrowSaga {
	@Autowired
	private transient CommandGateway commandGateway;

	@Autowired
	private transient QueryGateway queryGateway;

	@Autowired
	private transient BookGrpcClient bookGrpcClient;

	// Empty constructor required by Axon
	public BorrowSaga() {
		// Required empty constructor
	}

	@StartSaga
	@SagaEventHandler(associationProperty = "id")
	private void handle(CreateBorrowEvent event) {
		log.info("Borrowed in saga for BookId: {} and EmployeeId: {}", event.getBookId(), event.getEmployeeId());
		try {
			BookResponseModel model = bookGrpcClient.getBookDetail(event.getBookId());

			if (!model.getIsReady()) {
				log.info("Book {} is not ready", event.getBookId());
				throw new BusinessException(ErrorCode.BOOK_NOT_AVAILABLE,
						String.format("Book %s is not available for borrowing", event.getBookId()));
			}
			SagaLifecycle.associateWith("bookId", event.getBookId());
			bookGrpcClient.updateBookStatus(event.getBookId(), false);
		} catch (BusinessException e) {
			rollbackBorrowRecord(event.getId());
		}
	}

	@SagaEventHandler(associationProperty = "bookId")
	private void handle(UpdateBookStatusEvent event) {
		log.info("Book status updated for bookId: {}", event.getBookId());
		if (!event.getIsReady()) {
			log.info("Ending saga for bookId: {}", event.getBookId());
			SagaLifecycle.end();
		} else {
			log.error("Failed to update book status for bookId: {}", event.getBookId());
			throw new BusinessException(ErrorCode.BUSINESS_ERROR, "Failed to update book status");
		}
	}

	private void rollbackBorrowRecord(String id) {
		DeleteBorrowCommand command = DeleteBorrowCommand.builder()
				.id(id)
				.build();

		commandGateway.sendAndWait(command);
		SagaLifecycle.end();
	}
}
