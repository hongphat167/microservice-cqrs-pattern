package com.hongphat.borrowservice.config;

import com.hongphat.common_service.proto.CommonServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * GrpcConfig
 *
 * @author hongp
 * @description Happy Coding With Phat 😊😊
 * @since 7 :36 CH 18/01/2025
 */
@Configuration
@Slf4j
public class GrpcConfig {
	@Value("${grpc.client.bookservice.address}")
	private String bookServiceAddress;

	@Value("${grpc.client.bookservice.keep-alive-time}")
	private long bookServiceKeepAliveTime;

	@Value("${grpc.client.employeeservice.address}")
	private String employeeServiceAddress;

	@Value("${grpc.client.employeeservice.keep-alive-time}")
	private long employeeServiceKeepAliveTime;

	/**
	 * Book service channel managed channel.
	 *
	 * @return the managed channel
	 */
	@Bean(name = "bookServiceChannel")
	public ManagedChannel bookServiceChannel() {
		String[] parts = bookServiceAddress.split(":");
		String host = parts[0];
		int port = Integer.parseInt(parts[1]);

		ManagedChannel channel = NettyChannelBuilder.forAddress(host, port)
				.usePlaintext()
				.enableRetry()
				.keepAliveTime(bookServiceKeepAliveTime, TimeUnit.SECONDS)
				.build();

		log.info("Book service gRPC channel initialized: {}", channel);
		return channel;
	}

	/**
	 * Employee service channel managed channel.
	 *
	 * @return the managed channel
	 */
	@Bean(name = "employeeServiceChannel")
	public ManagedChannel employeeServiceChannel() {
		String[] parts = employeeServiceAddress.split(":");
		String host = parts[0];
		int port = Integer.parseInt(parts[1]);

		ManagedChannel channel = NettyChannelBuilder.forAddress(host, port)
				.usePlaintext()
				.enableRetry()
				.keepAliveTime(employeeServiceKeepAliveTime, TimeUnit.SECONDS)
				.build();

		log.info("Employee service gRPC channel initialized: {}", channel);
		return channel;
	}

	/**
	 * Book service stub common service grpc . common service blocking stub.
	 *
	 * @param channel the channel
	 * @return the common service grpc . common service blocking stub
	 */
	@Bean(name = "bookServiceStub")
	public CommonServiceGrpc.CommonServiceBlockingStub bookServiceStub(
			@Qualifier("bookServiceChannel") ManagedChannel channel) {
		return CommonServiceGrpc.newBlockingStub(channel);
	}

	/**
	 * Employee service stub common service grpc . common service blocking stub.
	 *
	 * @param channel the channel
	 * @return the common service grpc . common service blocking stub
	 */
	@Bean(name = "employeeServiceStub")
	public CommonServiceGrpc.CommonServiceBlockingStub employeeServiceStub(
			@Qualifier("employeeServiceChannel") ManagedChannel channel) {
		return CommonServiceGrpc.newBlockingStub(channel);
	}
}