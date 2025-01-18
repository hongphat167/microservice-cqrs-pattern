package com.hongphat.borrowservice.config;

import com.hongphat.common_service.proto.BookGrpcServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * GrpcConfig
 *
 * @author hongp
 * @description Happy Coding With Phat 😊😊
 * @since 7:36 CH 18/01/2025
 */
@Configuration
@Slf4j
public class GrpcConfig {
	@Value("${grpc.client.address}")
	private String address;

	@Value("${grpc.client.keep-alive-time}")
	private long keepAliveTime;

	@Bean
	public ManagedChannel grpcChannel() {
		String[] parts = address.split(":");
		String host = parts[0];
		int port = Integer.parseInt(parts[1]);

		ManagedChannel channel = NettyChannelBuilder.forAddress(host, port)
				.usePlaintext()
				.enableRetry()
				.keepAliveTime(keepAliveTime, TimeUnit.SECONDS)
				.build();

		log.info("gRPC channel initialized: {}", channel);
		return channel;
	}

	@Bean
	public BookGrpcServiceGrpc.BookGrpcServiceBlockingStub bookStub(ManagedChannel channel) {
		return BookGrpcServiceGrpc.newBlockingStub(channel);
	}
}
