package com.hongphat.employeeservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

/**
 * OpenApiConfig
 *
 * @author hongp
 * @description Happy Coding With Phat 😊😊
 * @since 11 :38 CH 09/01/2025
 */
@Configuration
@OpenAPIDefinition(
		info = @Info(
				title = "Employee Api Specification - Microservice",
				description = "Api document for employee-service",
				version = "1.0",
				contact = @Contact(
						name = "Hong Phat",
						email = "hogphat1607@gmail.com"
				),
				license = @License(
						name = "MIT License",
						url = "https://www.linkedin.com/in/hong-phat-499901267/"
				),
				termsOfService = "https://www.linkedin.com/in/hong-phat-499901267/"
		),
		servers = @Server(
				description = "Local ENV",
				url = "http://localhost:9002"
		)
)
public class OpenApiConfig {
}
