package com.hongphat.api_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;


/**
 * The type Api gateway application.
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication {

	/**
	 * The entry point of application.
	 *
	 * @param args the input arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

	/**
	 * Key resolver key resolver.
	 *
	 * @return the key resolver
	 */
	@Bean
	public KeyResolver keyResolver() {
		return exchange ->
				Mono.just(exchange.getRequest()
						.getRemoteAddress()
						.getAddress()
						.getHostAddress()
				);
	}

}