package com.hongphat.borrowservice.grpc_client;

import com.hongphat.borrowservice.command.event.UpdateBookStatusEvent;
import com.hongphat.borrowservice.model.BookResponseModel;
import com.hongphat.common_service.enumerate.ErrorCode;
import com.hongphat.common_service.exception.BusinessException;
import com.hongphat.microservice.cqrs.pattern.common.service.grpc.*;
import io.grpc.ManagedChannel;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.gateway.EventGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * BookGrpcClient
 *
 * @author hongp
 * @description Happy Coding With Phat 😊😊
 * @since 9 :52 CH 14/01/2025
 */
@Service
@Slf4j
public class BookGrpcClient {

	private static final int PORT = 6565;
	private static final String HOST = "localhost";

	@Autowired
	private EventGateway eventGateway;

	private ManagedChannel channel;
	private BookGrpcServiceGrpc.BookGrpcServiceBlockingStub bookStub;

	@PostConstruct
	public void init() {
		channel = NettyChannelBuilder.forAddress(HOST, PORT)
				.usePlaintext()
				.enableRetry()
				.keepAliveTime(10, TimeUnit.SECONDS)
				.build();
		bookStub = BookGrpcServiceGrpc.newBlockingStub(channel);
		log.info("BookGrpcClient initialized with channel: {}", channel);
	}

	@PreDestroy
	public void shutdown() {
		if (channel != null) {
			channel.shutdown();
			try {
				channel.awaitTermination(5, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				log.error("Error while shutting down channel", e);
				Thread.currentThread().interrupt();
			}
		}
	}

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