package com.hongphat.borrowservice.grpc.client;

import com.hongphat.borrowservice.command.event.UpdateBookStatusEvent;
import com.hongphat.borrowservice.model.BookResponseModel;
import com.hongphat.borrowservice.model.EmployeeResponseModel;
import com.hongphat.common_service.enumerate.ErrorCode;
import com.hongphat.common_service.exception.BusinessException;
import com.hongphat.common_service.proto.*;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.gateway.EventGateway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * BookGrpcClient
 *
 * @author hongp
 * @description Happy Coding With Phat 😊😊
 * @since 9 :52 CH 14/01/2025
 */
@Slf4j
@Component
public class BorrowGrpcClient {

	private final EventGateway eventGateway;
	private final CommonServiceGrpc.CommonServiceBlockingStub bookServiceStub;
	private final CommonServiceGrpc.CommonServiceBlockingStub employeeServiceStub;

	/**
	 * Instantiates a new Borrow grpc client.
	 *
	 * @param eventGateway        the event gateway
	 * @param bookServiceStub     the book service stub
	 * @param employeeServiceStub the employee service stub
	 */
	public BorrowGrpcClient(EventGateway eventGateway,
	                        @Qualifier("bookServiceStub")
	                        CommonServiceGrpc.CommonServiceBlockingStub bookServiceStub,
	                        @Qualifier("employeeServiceStub")
	                        CommonServiceGrpc.CommonServiceBlockingStub employeeServiceStub) {
		this.eventGateway = eventGateway;
		this.bookServiceStub = bookServiceStub;
		this.employeeServiceStub = employeeServiceStub;
	}

	/**
	 * Gets book detail.
	 *
	 * @param bookId the book id
	 * @return the book detail
	 */
	public BookResponseModel getBookDetail(String bookId) {
		try {
			GetBookDetailRequest request = GetBookDetailRequest.newBuilder()
					.setBookId(bookId)
					.build();

			GetBookDetailResponse response = bookServiceStub.getBookDetail(request);

			return BookResponseModel.builder()
					.id(response.getId())
					.name(response.getName())
					.author(response.getAuthor())
					.isReady(response.getIsReady())
					.build();

		} catch (StatusRuntimeException e) {
			log.error("Error calling book service: ", e);
			if (e.getStatus().getCode() == Status.Code.NOT_FOUND) {
				throw new BusinessException(ErrorCode.BUSINESS_ERROR, "Book not found");
			}
			throw new BusinessException(ErrorCode.BUSINESS_ERROR, "Error getting book details");
		}
	}

	/**
	 * Update book status.
	 *
	 * @param bookId     the book id
	 * @param isReady    the is ready
	 * @param employeeId the employee id
	 */
	public void updateBookStatus(String bookId, boolean isReady, String employeeId) {
		try {
			UpdateBookStatusRequest request = UpdateBookStatusRequest.newBuilder()
					.setId(bookId)
					.setIsReady(isReady)
					.build();

			UpdateBookStatusResponse response = bookServiceStub.updateBookStatus(request);

			if (!response.getSuccess()) {
				throw new BusinessException(ErrorCode.BUSINESS_ERROR, "Failed to update book status");
			}

			UpdateBookStatusEvent event = UpdateBookStatusEvent.builder()
					.bookId(bookId)
					.isReady(isReady)
					.employeeId(employeeId)
					.build();
			eventGateway.publish(event);

		} catch (StatusRuntimeException e) {
			log.error("Error updating book status: ", e);
			throw new BusinessException(ErrorCode.BUSINESS_ERROR, "Error updating book status");
		}
	}

	/**
	 * Gets employee detail.
	 *
	 * @param employeeId the employee id
	 * @return the employee detail
	 */
	public EmployeeResponseModel getEmployeeDetail(String employeeId) {
		try {
			GetDetailEmployeeRequest request = GetDetailEmployeeRequest.newBuilder()
					.setEmployeeId(employeeId)
					.build();

			GetDetailEmployeeResponse response = employeeServiceStub.getEmployeeDetail(request);

			return EmployeeResponseModel.builder()
					.id(response.getId())
					.firstName(response.getFirstName())
					.lastName(response.getLastName())
					.kin(response.getKin())
					.isDisciplined(response.getIsDisciplined())
					.build();
		} catch (StatusRuntimeException e) {

			log.error("Error calling employee service: ", e);
			if (e.getStatus().getCode() == Status.Code.NOT_FOUND) {
				throw new BusinessException(ErrorCode.BUSINESS_ERROR, "Employee not found");
			}
			throw new BusinessException(ErrorCode.BUSINESS_ERROR, "Error getting employee details");
		}
	}
}