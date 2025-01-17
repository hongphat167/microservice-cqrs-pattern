package com.hongphat.bookservice.grpc;

import com.hongphat.bookservice.command.command_handling.UpdateBookStatusCommand;
import com.hongphat.bookservice.query.model.response.BookResponseModel;
import com.hongphat.bookservice.query.queries.GetDetailBookQuery;
import com.hongphat.common_service.exception.BusinessException;
import com.hongphat.microservice.cqrs.pattern.common.service.grpc.*;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * BookGrpcService
 *
 * @author hongp
 * @description Happy Coding With Phat 😊😊
 * @since 9 :41 CH 14/01/2025
 */
@Slf4j
@Component
public class BookGrpcService extends BookGrpcServiceGrpc.BookGrpcServiceImplBase {

	private final QueryGateway queryGateway;
	private final CommandGateway commandGateway;
	private Server grpcServer;

	/**
	 * Instantiates a new Book grpc service.
	 *
	 * @param queryGateway   the query gateway
	 * @param commandGateway the command gateway
	 */
	protected BookGrpcService(QueryGateway queryGateway,
	                          CommandGateway commandGateway) {
		this.queryGateway = queryGateway;
		this.commandGateway = commandGateway;
	}

	/**
	 * Start grpc server.
	 */
	@PostConstruct
	public void startGrpcServer() {
		try {
			grpcServer = ServerBuilder.forPort(6565)
					.addService(this)
					.build()
					.start();

			log.info("gRPC Server started on port 6565");

			Runtime.getRuntime().addShutdownHook(new Thread(() -> {
				log.info("Shutting down gRPC server");
				if (grpcServer != null) {
					grpcServer.shutdown();
				}
				log.info("gRPC server shut down successfully");
			}));

		} catch (IOException e) {
			log.error("Failed to start gRPC server", e);
			throw new RuntimeException("Failed to start gRPC server", e);
		}
	}

	/**
	 * Stop grpc server.
	 */
	@PreDestroy
	public void stopGrpcServer() {
		if (grpcServer != null) {
			grpcServer.shutdown();
			try {
				grpcServer.awaitTermination(30, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				log.error("Error while shutting down gRPC server", e);
				Thread.currentThread().interrupt();
			}
		}
	}

	@Override
	public void getBookDetail(GetBookDetailRequest request, StreamObserver<GetBookDetailResponse> responseObserver) {
		log.info("Received getBookDetail request for bookId: {}", request.getBookId());
		try {
			BookResponseModel model = queryGateway.query(
					new GetDetailBookQuery(request.getBookId()),
					BookResponseModel.class
			).join();

			GetBookDetailResponse response = GetBookDetailResponse.newBuilder()
					.setId(model.getId())
					.setName(model.getName())
					.setAuthor(model.getAuthor())
					.setIsReady(model.getIsReady())
					.build();

			responseObserver.onNext(response);
			responseObserver.onCompleted();
			log.info("Successfully processed getBookDetail request for bookId: {}", request.getBookId());

		} catch (BusinessException e) {
			log.error("Book not found for bookId: {}", request.getBookId(), e);
			responseObserver.onError(
					Status.NOT_FOUND
							.withDescription("Book not found")
							.withCause(e)
							.asRuntimeException()
			);
		} catch (Exception e) {
			log.error("Internal error while processing getBookDetail for bookId: {}", request.getBookId(), e);
			responseObserver.onError(
					Status.INTERNAL
							.withDescription("Internal error occurred")
							.withCause(e)
							.asRuntimeException()
			);
		}
	}

	@Override
	public void updateBookStatus(UpdateBookStatusRequest request, StreamObserver<UpdateBookStatusResponse> responseObserver) {
		log.info("Received updateBookStatus request for bookId: {} with status: {}", request.getId(), request.getIsReady());
		try {
			UpdateBookStatusCommand command = UpdateBookStatusCommand.builder()
					.id(request.getId())
					.isReady(request.getIsReady())
					.build();

			commandGateway.sendAndWait(command);

			UpdateBookStatusResponse response = UpdateBookStatusResponse.newBuilder()
					.setSuccess(true)
					.build();

			responseObserver.onNext(response);
			responseObserver.onCompleted();
			log.info("Successfully updated book status for bookId: {}", request.getId());

		} catch (Exception e) {
			log.error("Failed to update book status for bookId: {}", request.getId(), e);
			responseObserver.onError(
					Status.INTERNAL
							.withDescription("Failed to update book status")
							.withCause(e)
							.asRuntimeException()
			);
		}
	}
}