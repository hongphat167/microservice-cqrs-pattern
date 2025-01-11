package com.hongphat.employeeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * The type Employee service application.
 *
 * @author hongp
 * @createDay 08 /01/2025
 * @description Happy Coding With Phat 😊😊
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan({"com.hongphat.employeeservice", "com.hongphat.common_service"})
@EnableAspectJAutoProxy
public class EmployeeServiceApplication {

	/**
	 * The entry point of application.
	 *
	 * @param args the input arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(EmployeeServiceApplication.class, args);
	}

}