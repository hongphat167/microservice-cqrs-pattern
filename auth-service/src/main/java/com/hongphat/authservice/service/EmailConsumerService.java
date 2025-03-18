package com.hongphat.authservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hongphat.authservice.command.event.EmailEvent;
import com.hongphat.common_service.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.RetriableException;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.DltStrategy;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;

/**
 * --------------------------------------------------------
 * EmailConsumerService
 *
 * @author: phatthh
 * @since: 16 /03/2025
 * @description: Dự án microservice của Phát --------------------------------------------------------
 */
@Service
@Slf4j
public class EmailConsumerService {
    private final EmailService emailService;
    private final ObjectMapper objectMapper;

    /**
     * Instantiates a new Email consumer service.
     *
     * @param emailService the email service
     * @param objectMapper the object mapper
     */
    public EmailConsumerService(EmailService emailService, ObjectMapper objectMapper) {
        this.emailService = emailService;
        this.objectMapper = objectMapper;
    }

    /**
     * Consume.
     *
     * @param message the message
     */
    @RetryableTopic(
            attempts = "5", // 4 topic + 1 Dead Letter Queue
            backoff = @Backoff(delay = 1000, multiplier = 2),
            dltStrategy = DltStrategy.FAIL_ON_ERROR,
            include = {RetriableException.class, RuntimeException.class}
    )
    @KafkaListener(topics = "email-notifications", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(String message) {
        try {
            log.info("Received email message: {}", message);
            EmailEvent emailEvent = objectMapper.readValue(message, EmailEvent.class);

            switch (emailEvent.getType()) {
                case REGISTRATION:
                    log.info("Processing registration email for: {}", emailEvent.getTo());
                    break;
                case PASSWORD_RESET:
                    log.info("Processing password reset email for: {}", emailEvent.getTo());
                    break;
            }

            emailService.sendEmail(
                    emailEvent.getTo(),
                    emailEvent.getSubject(),
                    emailEvent.getContent(),
                    emailEvent.isHtml(),
                    null
            );

            log.info("Email sent successfully to {}", emailEvent.getTo());
        } catch (Exception e) {
            log.error("Error processing email message: {}", message, e);
        }
    }

    /**
     * Process messages that failed after all retry attempts.
     *
     * @param message the failed message
     */
    @DltHandler
    void processDltMessage(@Payload String message) {
        log.error("Failed to process email after all retries. Message: {}", message);
        try {
            EmailEvent emailEvent = objectMapper.readValue(message, EmailEvent.class);
            log.error("Failed email delivery to: {}, subject: {}, type: {}",
                    emailEvent.getTo(), emailEvent.getSubject(), emailEvent.getType());
        } catch (Exception e) {
            log.error("Could not parse failed email message", e);
        }
    }
}
