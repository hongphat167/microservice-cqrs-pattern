package com.hongphat.bookservice.config;

import com.hongphat.bookservice.grpc.BookGrpcService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * GrpcConfig
 *
 * @author hongp
 * @description Happy Coding With Phat 😊😊
 * @since 7 :21 CH 18/01/2025
 */
@Configuration
@Slf4j
public class GrpcConfig {

	@Value("${grpc.server.port}")
	private int grpcPort;


	/**
	 * Grpc server server.
	 *
	 * @param bookService the book service
	 * @return the server
	 */
	@Bean
	public Server grpcServer(BookGrpcService bookService) {
		try {
			Server server = ServerBuilder.forPort(grpcPort)
					.addService(bookService)
					.build()
					.start();

			log.info("gRPC Server started on port {}", grpcPort);

			Runtime.getRuntime().addShutdownHook(new Thread(() -> {
				log.info("Shutting down gRPC server");
				if (server != null) {
					server.shutdown();
				}
				log.info("gRPC server shut down successfully");
			}));

			return server;

		} catch (IOException e) {
			log.error("Failed to start gRPC server", e);
			throw new RuntimeException("Failed to start gRPC server", e);
		}
	}
}
