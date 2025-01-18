package com.hongphat.borrowservice.grpc_client;

import com.hongphat.borrowservice.command.event.UpdateBookStatusEvent;
import com.hongphat.borrowservice.model.BookResponseModel;
import com.hongphat.common_service.enumerate.ErrorCode;
import com.hongphat.common_service.exception.BusinessException;
import com.hongphat.common_service.proto.*;
import io.grpc.ManagedChannel;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.gateway.EventGateway;
import org.springframework.context.annotation.Bean;
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
public class BookGrpcClient {

	private final EventGateway eventGateway;

	private final BookGrpcServiceGrpc.BookGrpcServiceBlockingStub bookStub;

	/**
	 * Instantiates a new Book grpc client.
	 *
	 * @param eventGateway the event gateway
	 * @param bookStub     the book stub
	 */
	public BookGrpcClient(EventGateway eventGateway, BookGrpcServiceGrpc.BookGrpcServiceBlockingStub bookStub) {
		this.eventGateway = eventGateway;
		this.bookStub = bookStub;
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

			GetBookDetailResponse response = bookStub.getBookDetail(request);

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
	 * @param bookId  the book id
	 * @param isReady the is ready
	 */
	public void updateBookStatus(String bookId, boolean isReady) {
		try {
			UpdateBookStatusRequest request = UpdateBookStatusRequest.newBuilder()
					.setId(bookId)
					.setIsReady(isReady)
					.build();

			UpdateBookStatusResponse response = bookStub.updateBookStatus(request);

			if (!response.getSuccess()) {
				throw new BusinessException(ErrorCode.BUSINESS_ERROR, "Failed to update book status");
			}

			UpdateBookStatusEvent event = UpdateBookStatusEvent.builder()
					.bookId(bookId)
					.isReady(isReady)
					.build();
			eventGateway.publish(event);

		} catch (StatusRuntimeException e) {
			log.error("Error updating book status: ", e);
			throw new BusinessException(ErrorCode.BUSINESS_ERROR, "Error updating book status");
		}
	}
}