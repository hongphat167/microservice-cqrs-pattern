package com.hongphat.api_gateway.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Key auth filter.
 */
@Component
public class KeyAuthFilter extends AbstractGatewayFilterFactory<KeyAuthFilter.Config> {

	@Value("${apiKey}")
	private String apiKey;

	private final ObjectMapper objectMapper;

	/**
	 * Instantiates a new Key auth filter.
	 *
	 * @param objectMapper the object mapper
	 */
	public KeyAuthFilter(ObjectMapper objectMapper) {
		super(Config.class);
		this.objectMapper = objectMapper;
	}

	@Override
	public GatewayFilter apply(Config config) {
		return ((exchange, chain) -> {
			if (!exchange.getRequest().getHeaders().containsKey("apiKey")) {
				return handleException(exchange, "Missing apiKey", HttpStatus.UNAUTHORIZED);
			}

			String key = exchange.getRequest().getHeaders().getFirst("apiKey");

			if (key == null || !key.equals(apiKey)) {
				return handleException(exchange, "Invalid apiKey", HttpStatus.UNAUTHORIZED);
			}

			ServerHttpRequest request = exchange.getRequest();
			return chain.filter(exchange.mutate().request(request).build());
		});
	}

	private Mono<Void> handleException(ServerWebExchange exchange, String message, HttpStatus status) {
		ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(status);
		response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

		Map<String, Object> errorDetails = new HashMap<>();
		errorDetails.put("timestamp", LocalDateTime.now().toString());
		errorDetails.put("status", status.value());
		errorDetails.put("error", status.getReasonPhrase());
		errorDetails.put("message", message);
		errorDetails.put("path", exchange.getRequest().getPath().value());
		errorDetails.put("method", exchange.getRequest().getMethod().name());

		try {
			String errorResponse = objectMapper.writeValueAsString(errorDetails);
			byte[] bytes = errorResponse.getBytes(StandardCharsets.UTF_8);
			return response.writeWith(Mono.just(response.bufferFactory().wrap(bytes)));
		} catch (JsonProcessingException e) {
			String fallbackResponse = String.format(
					"{\"message\":\"%s\",\"status\":%d,\"error\":\"Internal Server Error\"}",
					"Error processing response",
					HttpStatus.INTERNAL_SERVER_ERROR.value()
			);
			byte[] bytes = fallbackResponse.getBytes(StandardCharsets.UTF_8);
			return response.writeWith(Mono.just(response.bufferFactory().wrap(bytes)));
		}
	}

	/**
	 * The type Config.
	 */
	public static class Config {
	}
}