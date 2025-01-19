package com.hongphat.bookservice.grpc;

import com.hongphat.bookservice.command.command_handling.UpdateBookStatusCommand;
import com.hongphat.bookservice.query.model.response.BookResponseModel;
import com.hongphat.bookservice.query.queries.GetDetailBookQuery;
import com.hongphat.common_service.exception.BusinessException;
import com.hongphat.common_service.proto.*;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;

/**
 * BookGrpcService
 *
 * @author hongp
 * @description Happy Coding With Phat 😊😊
 * @since 9 :41 CH 14/01/2025
 */
@Slf4j
@GrpcService
public class BookGrpcService extends CommonServiceGrpc.CommonServiceImplBase {

	private final QueryGateway queryGateway;
	private final CommandGateway commandGateway;

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