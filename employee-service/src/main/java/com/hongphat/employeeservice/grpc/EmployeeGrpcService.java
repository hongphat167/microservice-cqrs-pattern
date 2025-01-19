package com.hongphat.employeeservice.grpc;

import com.hongphat.common_service.exception.BusinessException;
import com.hongphat.common_service.proto.CommonServiceGrpc;
import com.hongphat.common_service.proto.GetDetailEmployeeRequest;
import com.hongphat.common_service.proto.GetDetailEmployeeResponse;
import com.hongphat.employeeservice.query.model.response.EmployeeModelResponse;
import com.hongphat.employeeservice.query.queries.GetEmployeeDetailQuery;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.stereotype.Component;

/**
 * EmployeeGrpcService
 *
 * @author hongp
 * @description Happy Coding With Phat 😊😊
 * @since 8 :13 CH 18/01/2025
 */
@Slf4j
@Component
public class EmployeeGrpcService extends CommonServiceGrpc.CommonServiceImplBase {

	private final QueryGateway queryGateway;

	/**
	 * Instantiates a new Employee grpc service.
	 *
	 * @param queryGateway the query gateway
	 */
	public EmployeeGrpcService(QueryGateway queryGateway) {
		this.queryGateway = queryGateway;
	}


	@Override
	public void getEmployeeDetail(GetDetailEmployeeRequest request, StreamObserver<GetDetailEmployeeResponse> responseObserver) {
		log.info("Received getEmployeeDetail request for employeeId: {}", request.getEmployeeId());

		try {
			EmployeeModelResponse model = queryGateway.query(
					new GetEmployeeDetailQuery(request.getEmployeeId()),
					EmployeeModelResponse.class
			).join();

			GetDetailEmployeeResponse response = GetDetailEmployeeResponse.newBuilder()
					.setId(model.getId())
					.setFirstName(model.getFirstName())
					.setLastName(model.getLastName())
					.setKin(model.getKin())
					.setIsDisciplined(model.getIsDisciplined())
					.build();

			responseObserver.onNext(response);
			responseObserver.onCompleted();

		} catch (BusinessException e) {
			log.error("Employee Not Found For employeeId: {}", request.getEmployeeId(), e);
			responseObserver.onError(
					Status.NOT_FOUND
							.withDescription("Employee Not Found")
							.withCause(e)
							.asRuntimeException()
			);
		} catch (Exception e) {
			log.error("Internal error while processing getEmployeeDetail for employeeId: {}", request.getEmployeeId(), e);
			responseObserver.onError(
					Status.INTERNAL
							.withDescription("Internal error occurred")
							.withCause(e)
							.asRuntimeException()
			);
		}
	}
}
