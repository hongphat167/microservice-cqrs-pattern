package com.hongphat.authservice.command.command_handling;

import lombok.*;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

/**
 * The type Register user command.
 */
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class RegisterUserCommand {
    @TargetAggregateIdentifier
    private String id;
    private String username;
    private String email;
    private String password;
}