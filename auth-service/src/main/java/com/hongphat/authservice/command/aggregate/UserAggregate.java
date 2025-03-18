package com.hongphat.authservice.command.aggregate;

import com.hongphat.authservice.command.command_handling.RegisterUserCommand;
import com.hongphat.authservice.command.command_handling.ResetPasswordCommand;
import com.hongphat.authservice.command.event.PasswordResetRequestedEvent;
import com.hongphat.authservice.command.event.UserRegisteredEvent;
import com.hongphat.authservice.module.factory.IUserFactory;
import com.hongphat.common_service.enumerate.ErrorCode;
import com.hongphat.common_service.exception.BusinessException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * The type User aggregate.
 */
@Aggregate
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserAggregate {
    @AggregateIdentifier
    private String id;
    private String username;
    private String email;
    private String password;
    private String resetToken;

    /**
     * Instantiates a new User aggregate.
     *
     * @param command         the command
     * @param passwordEncoder the password encoder
     * @param userFactory     the user factory
     */
    @CommandHandler
    public UserAggregate(RegisterUserCommand command, PasswordEncoder passwordEncoder, IUserFactory userFactory) {
        try {
            userFactory.findByUsername(command.getUsername());
            throw new BusinessException(ErrorCode.USERNAME_ALREADY_EXISTS,
                    "Username already exists: " + command.getUsername());
        } catch (BusinessException e) {
            if (!e.getErrorCode().getCode().equals(ErrorCode.USER_NOT_FOUND.getCode())) {
                throw e;
            }
        }

        try {
            userFactory.findByEmail(command.getEmail());
            throw new BusinessException(ErrorCode.EMAIL_ALREADY_EXISTS,
                    "Email already exists: " + command.getEmail());
        } catch (BusinessException e) {
            if (!e.getErrorCode().getCode().equals(ErrorCode.USER_NOT_FOUND.getCode())) {
                throw e;
            }
        }

        String hashedPassword = passwordEncoder.encode(command.getPassword());

        AggregateLifecycle.apply(
                UserRegisteredEvent.builder()
                        .id(command.getId())
                        .username(command.getUsername())
                        .email(command.getEmail())
                        .hashedPassword(hashedPassword)
                        .build()
        );
    }

    /**
     * Handle string.
     *
     * @param command the command
     */
    @CommandHandler
    public void handle(ResetPasswordCommand command) {

        AggregateLifecycle.apply(
                PasswordResetRequestedEvent.builder()
                        .id(command.getId())
                        .email(command.getEmail())
                        .resetToken(command.getResetToken())
                        .build()
        );
    }

    /**
     * On.
     *
     * @param event the event
     */
    @EventSourcingHandler
    public void on(UserRegisteredEvent event) {
        this.id = event.getId();
        this.username = event.getUsername();
        this.email = event.getEmail();
        this.password = event.getHashedPassword();
    }

    /**
     * On.
     *
     * @param event the event
     */
    @EventSourcingHandler
    public void on(PasswordResetRequestedEvent event) {
        this.resetToken = event.getResetToken();
    }
}