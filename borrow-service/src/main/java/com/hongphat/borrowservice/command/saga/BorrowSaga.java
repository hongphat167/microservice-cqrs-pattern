package com.hongphat.borrowservice.command.saga;

import com.hongphat.borrowservice.command.command_handling.DeleteBorrowCommand;
import com.hongphat.borrowservice.command.event.CreateBorrowEvent;
import com.hongphat.borrowservice.command.event.DeleteBorrowEvent;
import com.hongphat.borrowservice.command.event.UpdateBookStatusEvent;
import com.hongphat.borrowservice.grpc.client.BorrowGrpcClient;
import com.hongphat.borrowservice.model.BookResponseModel;
import com.hongphat.borrowservice.model.EmployeeResponseModel;
import com.hongphat.common_service.enumerate.ErrorCode;
import com.hongphat.common_service.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * The type Borrow saga.
 */
@Saga
@Slf4j
public class BorrowSaga {

	@Autowired
	private transient CommandGateway commandGateway;

	@Autowired
	private transient BorrowGrpcClient borrowGrpcClient;

	private String borrowId;
	private String currentBookId;
	private String currentEmployeeId;
	private boolean isRollbackInProgress = false;
	private boolean needRollbackStatus = false;

	/**
	 * Instantiates a new Borrow saga.
	 */
	public BorrowSaga() {
	}

	@StartSaga
	@SagaEventHandler(associationProperty = "id")
	private void handle(CreateBorrowEvent event) {
		log.info("Borrowed in saga for BookId: {} and EmployeeId: {}", event.getBookId(), event.getEmployeeId());

		this.borrowId = event.getId();
		this.currentBookId = event.getBookId();
		this.currentEmployeeId = event.getEmployeeId();

		BookResponseModel model = borrowGrpcClient.getBookDetail(event.getBookId());

		if (!model.getIsReady()) {
			log.info("Book {} is not ready", event.getBookId());
			rollBackBorrowRecordOnly(this.borrowId);
			throw new BusinessException(ErrorCode.BOOK_NOT_AVAILABLE,
					ErrorCode.BOOK_NOT_AVAILABLE.getMessage());
		}

		needRollbackStatus = true;
		SagaLifecycle.associateWith("bookId", event.getBookId());
		borrowGrpcClient.updateBookStatus(
				event.getBookId(),
				false,
				event.getEmployeeId()
		);
	}

	@EndSaga
	@SagaEventHandler(associationProperty = "bookId")
	private void handle(UpdateBookStatusEvent event) {
		if (isRollbackInProgress) {
			log.info("Rollback completed for book: {}", event.getBookId());
			return;
		}

		log.info("Check employee is disciplined for employeeId: {}", event.getEmployeeId());
		try {
			EmployeeResponseModel model = borrowGrpcClient.getEmployeeDetail(event.getEmployeeId());

			if (!model.getIsDisciplined()) {
				log.info("Employee {} is not disciplined", event.getEmployeeId());
				handleFullRollback(event.getBookId(), event.getEmployeeId());
				throw new BusinessException(ErrorCode.EMPLOYEE_IS_DISCIPLINED,
						ErrorCode.EMPLOYEE_IS_DISCIPLINED.getMessage());
			}
			log.info("Book {} is borrowing with employee {}", event.getBookId(), event.getEmployeeId());
		} catch (BusinessException e) {
			handleFullRollback(event.getBookId(), event.getEmployeeId());
		}
	}

	@EndSaga
	@SagaEventHandler(associationProperty = "id")
	private void handle(DeleteBorrowEvent event) {
		log.info("Book rollback event in Saga: {}", event.getId());
	}

	private void rollBackBorrowRecordOnly(String borrowId) {
		log.info("Rolling back borrow record only for id: {}", borrowId);
		DeleteBorrowCommand command = DeleteBorrowCommand.builder()
				.id(borrowId)
				.build();
		commandGateway.sendAndWait(command);
	}

	private void handleFullRollback(String bookId, String employeeId) {
		isRollbackInProgress = true;

		if (needRollbackStatus && bookId != null) {
			log.info("Rolling back book status for bookId: {}", bookId);
			borrowGrpcClient.updateBookStatus(bookId, true, employeeId);
		}

		if (this.borrowId != null) {
			rollBackBorrowRecordOnly(this.borrowId);
		}
	}
}