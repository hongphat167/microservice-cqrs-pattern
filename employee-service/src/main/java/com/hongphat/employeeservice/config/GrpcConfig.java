package com.hongphat.employeeservice.config;

import com.hongphat.employeeservice.grpc.EmployeeGrpcService;
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
 * @since 8 :11 CH 18/01/2025
 */
@Slf4j
@Configuration
public class GrpcConfig {

	@Value("${grpc.server.port}")
	private int grpcPort;

	/**
	 * Grpc server server.
	 *
	 * @param employeeGrpcService the employee grpc service
	 * @return the server
	 */
	@Bean
	public Server grpcServer(EmployeeGrpcService employeeGrpcService) {
		try {
			Server server = ServerBuilder.forPort(grpcPort)
					.addService(employeeGrpcService)
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
