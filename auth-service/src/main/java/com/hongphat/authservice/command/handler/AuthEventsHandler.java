package com.hongphat.authservice.command.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hongphat.authservice.command.event.PasswordResetRequestedEvent;
import com.hongphat.authservice.command.event.UserRegisteredEvent;
import com.hongphat.authservice.command.model.UserModel;
import com.hongphat.authservice.module.factory.IUserFactory;
import com.hongphat.common_service.enumerate.ErrorCode;
import com.hongphat.common_service.exception.BusinessException;
import com.hongphat.common_service.model.EmailEvent;
import com.hongphat.common_service.service.KafkaService;
import org.axonframework.eventhandling.DisallowReplay;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * The type Auth events handler.
 */
@Component
public class AuthEventsHandler {
    private static final String EMAIL_TOPIC = "email-notifications";
    private final IUserFactory userFactory;
    private final KafkaService kafkaService;
    private final ObjectMapper objectMapper;
    @Value("${app.frontend-url:http://localhost:3000}")
    private String frontendUrl;

    /**
     * Instantiates a new Auth events handler.
     *
     * @param userFactory  the user factory
     * @param kafkaService the kafka service
     * @param objectMapper the object mapper
     */
    protected AuthEventsHandler(IUserFactory userFactory,
                                KafkaService kafkaService,
                                ObjectMapper objectMapper) {
        this.userFactory = userFactory;
        this.objectMapper = objectMapper;
        this.kafkaService = kafkaService;
    }

    /**
     * Handle.
     *
     * @param event the event
     */
    @EventHandler
    @DisallowReplay
    public void handle(UserRegisteredEvent event) {
        try {
            UserModel user = UserModel.builder()
                    .id(event.getId())
                    .username(event.getUsername())
                    .email(event.getEmail())
                    .password(event.getHashedPassword())
                    .build();

            userFactory.create(user);

            String emailContent = "Chào Mừng Bạn Đến Với SPA Quyên Hồ " +
                    event.getUsername() + "!\n\n" + "Chúc mừng bạn đã tạo tài khoản thành công .\n\n" +
                    "Regards,\n" +
                    "The Support Team";

            EmailEvent emailEvent = EmailEvent.builder()
                    .to(event.getEmail())
                    .subject("Chào Mừng Bạn Đến Với SPA Quyên Hồ")
                    .content(emailContent)
                    .isHtml(false)
                    .type(EmailEvent.EmailType.REGISTRATION)
                    .build();

            String emailEventJson = objectMapper.writeValueAsString(emailEvent);
            kafkaService.sendMessage(EMAIL_TOPIC, emailEventJson);
        } catch (BusinessException | JsonProcessingException e) {
            throw new BusinessException(ErrorCode.BUSINESS_ERROR, e.getMessage());
        }
    }

    /**
     * Handle.
     *
     * @param event the event
     */
    @EventHandler
    @DisallowReplay
    public void handle(PasswordResetRequestedEvent event) {
        try {
            Instant tokenExpiry = Instant.now().plus(24, ChronoUnit.HOURS);
            userFactory.updatePasswordResetToken(event.getEmail(), event.getResetToken(), tokenExpiry);

            String resetLink = frontendUrl + "/reset-password/" + event.getResetToken();

            String emailContent = "<p>You requested a password reset. Click the link below to reset your password:</p>" +
                    "<p><a href=\"" + resetLink + "\">Reset Password</a></p>" +
                    "<p>This link will expire in 1 hour.</p>" +
                    "<p>If you did not request a password reset, please ignore this email.</p>";

            EmailEvent emailEvent = EmailEvent.builder()
                    .to(event.getEmail())
                    .subject("Password Reset Request")
                    .content(emailContent)
                    .isHtml(true)
                    .type(EmailEvent.EmailType.PASSWORD_RESET)
                    .build();
            String emailEventJson = objectMapper.writeValueAsString(emailEvent);
            kafkaService.sendMessage(EMAIL_TOPIC, emailEventJson);
        } catch (BusinessException | JsonProcessingException e) {
            throw new BusinessException(ErrorCode.BUSINESS_ERROR, e.getMessage());
        }
    }
}