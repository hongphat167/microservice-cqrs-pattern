package com.hongphat.common_service.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * KafkaService
 *
 * @author hongp
 * @description Happy Coding With Phat 😊😊
 * @since 11 :26 CH 11/01/2025
 */
@Service
@Slf4j
public class KafkaService {

	private final KafkaTemplate<String, String> kafkaTemplate;

	/**
	 * Instantiates a new Kafka service.
	 *
	 * @param kafkaTemplate the kafka template
	 */
	protected KafkaService(KafkaTemplate<String, String> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	/**
	 * Send message.
	 *
	 * @param topic   the topic
	 * @param message the message
	 */
	public void sendMessage(String topic, String message) {
		kafkaTemplate.send(topic, message);
		log.info("Message sent to topic {} : {}", topic, message);
	}
}
