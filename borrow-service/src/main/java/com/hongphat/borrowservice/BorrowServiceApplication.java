package com.hongphat.borrowservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * BorrowServiceApplication
 *
 * @author hongp
 * @description Happy Coding With Phat 😊😊
 * @since 1 :30 CH 12/01/2025
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan({"com.hongphat.borrowservice", "com.hongphat.common_service"})
public class BorrowServiceApplication {
	/**
	 * The entry point of application.
	 *
	 * @param args the input arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(BorrowServiceApplication.class, args);

	}
}
