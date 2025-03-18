package com.hongphat.notificationservice.event;

import com.hongphat.common_service.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.RetriableException;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.DltStrategy;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static com.hongphat.common_service.constant.EmailTemplateConstants.EMAIL_TEMPLATE;

/**
 * ConsumerEvent
 *
 * @author hongp
 * @description Happy Coding With Phat 😊😊
 * @since 11 :29 CH 11/01/2025
 */
@Component
@Slf4j
public class ConsumerEvent {

	private final EmailService emailService;

	/**
	 * Instantiates a new Consumer event.
	 *
	 * @param emailService the email service
	 */
	protected ConsumerEvent(EmailService emailService) {
		this.emailService = emailService;
	}

	/**
	 * Listen.
	 *
	 * @param message the message
	 */
	@RetryableTopic(
			attempts = "5", // 4 topic + 1 Dead Letter Queue
			backoff = @Backoff(delay = 1000, multiplier = 2),
			dltStrategy = DltStrategy.FAIL_ON_ERROR,
			include = {RetriableException.class, RuntimeException.class}
	)
	@KafkaListener(topics = "test", containerFactory = "kafkaListenerContainerFactory")
	public void listen(String message) {
		log.info("Received message: {}", message);
	}

	/**
	 * Process dlt message.
	 *
	 * @param message the message
	 */
	@DltHandler
	void processDltMessage(@Payload String message) {
		log.info("Dlt Received message: {}", message);
	}

	@KafkaListener(topics = "testEmail", containerFactory = "kafkaListenerContainerFactory")
	public void listenEmail(String message) {
		log.info("Received email message: {}", message);

		String personalizedContent = EMAIL_TEMPLATE
				.replace("%recipient_name%", "Hồng Phát")
				.replace("%notification_content%", "bạn đã nhận được một phần quà trị giá 10,000,000 đồng")
				.replace("%timestamp%", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")))
				.replace("%reference_code%", "REF-" + UUID.randomUUID().toString().substring(0, 8))
				.replace("%status%", "Đang xử lý")
				.replace("%action_url%", "https://yourdomain.com/action");

		emailService.sendEmail(
				message,
				"[Công ty TNNH Hồng Phát] Thư chúc mừng",
				personalizedContent,
				true,
				null
		);
	}
}
